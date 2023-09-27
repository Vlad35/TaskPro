package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.UserDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;
import ru.Vlad.Spring.TaskManager.TaskPro.utils.UserDTOValidator;

@Controller
public class AuthController {
    private final UserDTOValidator userDTOValidator;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserDTOValidator userDTOValidator, UserService userService, ModelMapper modelMapper) {
        this.userDTOValidator = userDTOValidator;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "views/Auth/Login";
    }

    @GetMapping("/auth/registration")
    public String registrationPage(@ModelAttribute UserDTO userDTO) {
        return "views/Auth/Registration";
    }

    @PostMapping("/auth/registration")
    public String postRegistration(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        userDTOValidator.validate(userDTO,bindingResult);
        if(bindingResult.hasErrors()) {
            return "views/Auth/Registration";
        }
        User user = convertToUser(userDTO);
        userService.createUser(user);
        return "redirect:/auth/login";
    }


    @GetMapping("/auth/denied")
    public String accessDenied() {
        return "views/Auth/Access_Denied";
    }
    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }
}
