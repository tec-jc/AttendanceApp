package org.esfe.controllers;

import org.esfe.entities.Group;
import org.esfe.entities.Teacher;
import org.esfe.services.interfaces.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("teachers")
public class TeacherController {
    @Autowired
    private ITeacherService teacherService;
    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Teacher> teachers = teacherService.findAll(pageable);
        model.addAttribute("teachers", teachers);

        int totalPages = teachers.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "teacher/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Teacher teacher = teacherService.findOneById(id).get();
        model.addAttribute("teacher", teacher);
        return "teacher/details";
    }

    @GetMapping("/create")
    public String create(Teacher teacher){
        return "teacher/create";
    }

    @PostMapping("/save")
    public String save(Teacher teacher, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(teacher);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "teacher/create";
        }

        teacherService.createOrEditOne(teacher);
        attributes.addFlashAttribute("msg", "Docente creado correctamente");
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Teacher teacher = teacherService.findOneById(id).get();
        model.addAttribute("teacher", teacher);
        return "teacher/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Teacher teacher = teacherService.findOneById(id).get();
        model.addAttribute("teacher", teacher);
        return "teacher/delete";
    }

    @PostMapping("/delete")
    public String delete(Teacher teacher, RedirectAttributes attributes){
        teacherService.deleteOneById(teacher.getId());
        attributes.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/teachers";
    }
}
