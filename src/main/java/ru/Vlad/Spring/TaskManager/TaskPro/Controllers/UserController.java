package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.UserDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.RoleService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String index(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "views/Users/User_Index";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") long id,Model model) {
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()) {
            model.addAttribute("user",user.get());
            return "views/Users/User_Show";
        }
        return "views/Users/UserNotFound";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }

    @GetMapping("/edit/{id}")
    public String editUserPage(@PathVariable("id") long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "views/Users/User_Edit";
        }
        return "views/Users/UserNotFound";
    }



    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute UserDTO userDTO) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            modelMapper.map(userDTO, user);

            userService.updateUser(user);

            return "redirect:/users";
        }
        return "views/Users/UserNotFound";
    }
}
