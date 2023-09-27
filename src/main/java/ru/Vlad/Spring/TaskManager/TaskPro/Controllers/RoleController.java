package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.RoleDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.RoleService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public String createRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "views/Roles/Role_Create";
    }

    @PostMapping("/create")
    public String createRole(@ModelAttribute RoleDTO roleDTO) {
        Role role = convertToRole(roleDTO);
        roleService.createRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/{id}")
    public String showRole(@PathVariable("id") long id, Model model) {
        Optional<Role> role = roleService.getRoleById(id);
        if(role.isPresent()) {
            model.addAttribute("role",role.get());
            return "views/Roles/Role_Show";
        }else  {
            return "views/Roles/RoleNotFound";
        }
    }

    @GetMapping("/edit/{id}")
    public String editRolePage(@PathVariable("id") long id,Model model) {
        Optional<Role> role = roleService.getRoleById(id);
        if(role.isPresent()) {
            model.addAttribute("role",role.get());
            return "views/Roles/Role_Edit";
        }
        return "views/Roles/RoleNotFound";
    }

    @PostMapping("/edit/{id}")
    public String editRole(@PathVariable("id") long id,RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleService.getRoleById(id);
        if(optionalRole.isPresent()) {
            Role role = optionalRole.get();
            modelMapper.map(roleDTO,role);
            roleService.updateRole(role);

            return "redirect:/roles";
        }
        return "views/Roles/RoleNotFound";
    }

    private Role convertToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO,Role.class);
    }
}
