package org.esfe.controllers;

import org.esfe.entities.Group;
import org.esfe.entities.Teacher;
import org.esfe.entities.TeacherGroup;
import org.esfe.services.interfaces.IGroupService;
import org.esfe.services.interfaces.ITeacherGroupService;
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
@RequestMapping("/asigns")
public class TeacherGroupController {
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private ITeacherGroupService teacherGroupService;

    @GetMapping()
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<TeacherGroup> asigns = teacherGroupService.getAllPaged(pageable);
        model.addAttribute("asigns", asigns);

        int totalPages = asigns.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "asign/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());

        return "asign/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer teacherId, @RequestParam Integer groupId,
                       @RequestParam Integer year, @RequestParam String semester,
                       RedirectAttributes attributes){
        Teacher teacher = teacherService.findOneById(teacherId).get();
        Group group = groupService.findOneById(groupId).get();

        if(teacher != null && group != null){
            TeacherGroup teacherGroup = new TeacherGroup();
            teacherGroup.setTeacher(teacher);
            teacherGroup.setGroup(group);
            teacherGroup.setYear(year);
            teacherGroup.setSemester(semester);

            teacherGroupService.save(teacherGroup);
            attributes.addFlashAttribute("msg", "Asignación creada correctamente");
        }

        return "redirect:/asigns";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        TeacherGroup teacherGroup = teacherGroupService.getById(id);
        model.addAttribute("teacherGroup", teacherGroup);
        return "asign/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        TeacherGroup teacherGroup = teacherGroupService.getById(id);
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("teacherGroup", teacherGroup);
        return "asign/edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam Integer teacherId, @RequestParam Integer groupId,
                       @RequestParam Integer year, @RequestParam String semester, RedirectAttributes attributes){
        Teacher teacher = teacherService.findOneById(teacherId).get();
        Group group = groupService.findOneById(groupId).get();

        if(teacher != null && group != null){
            TeacherGroup teacherGroup = new TeacherGroup();
            teacherGroup.setId(id);
            teacherGroup.setTeacher(teacher);
            teacherGroup.setGroup(group);
            teacherGroup.setYear(year);
            teacherGroup.setSemester(semester);

            teacherGroupService.save(teacherGroup);
            attributes.addFlashAttribute("msg", "Asignación modificada correctamente");
        }

        return "redirect:/asigns";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        TeacherGroup teacherGroup = teacherGroupService.getById(id);
        model.addAttribute("teacherGroup", teacherGroup);
        return "asign/delete";
    }

    @PostMapping("/delete")
    public String delete(TeacherGroup teacherGroup, RedirectAttributes attributes){
        teacherGroupService.deleteById(teacherGroup.getId());
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente");
        return "redirect:/asigns";
    }
}