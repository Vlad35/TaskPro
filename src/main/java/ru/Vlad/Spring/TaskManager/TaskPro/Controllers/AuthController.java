package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.UserDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.RoleService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;
import ru.Vlad.Spring.TaskManager.TaskPro.utils.UserDTOValidator;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserDTOValidator userValidator;

    @Autowired
    public AuthController(RoleService roleService, UserService userService, ModelMapper modelMapper, UserDTOValidator userValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "views/Auth/Login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute UserDTO userDTO) {
        return "views/Auth/Registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO,bindingResult);
        if(bindingResult.hasErrors()) {
            return "views/Auth/Registration";
        }
        User user = convertToUser(userDTO);
        userService.createUser(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/denied")
    public String accessDenied() {
        return "views/Auth/Access_Denied";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }
}
