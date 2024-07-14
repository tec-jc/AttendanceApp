package org.esfe.controllers;

import org.esfe.entities.Group;
import org.esfe.services.interfaces.IGroupService;
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
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private IGroupService groupService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Group> groups = groupService.findAll(pageable);
        model.addAttribute("groups", groups);

        int totalPages = groups.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "group/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Group group = groupService.findOneById(id).get();
        model.addAttribute("group", group);
        return "group/details";
    }

    @GetMapping("/create")
    public String create(Group group){
        return "group/create";
    }

    @PostMapping("/save")
    public String save(Group group, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(group);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "group/create";
        }

        groupService.createOrEditOne(group);
        attributes.addFlashAttribute("msg", "Grupo creado correctamente");
        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Group group = groupService.findOneById(id).get();
        model.addAttribute("group", group);
        return "group/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Group group = groupService.findOneById(id).get();
        model.addAttribute("group", group);
        return "group/delete";
    }

    @PostMapping("/delete")
    public String delete(Group group, RedirectAttributes attributes){
        groupService.deleteOneById(group.getId());
        attributes.addFlashAttribute("msg", "Grupo eliminado correctamente");
        return "redirect:/groups";
    }
}
