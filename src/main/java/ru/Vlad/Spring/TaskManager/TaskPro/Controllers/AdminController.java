package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.UserDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.CommentService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.RoleService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.TaskService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;
import ru.Vlad.Spring.TaskManager.TaskPro.utils.UserDTOValidator;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(RoleService roleService, UserService userService, TaskService taskService, CommentService commentService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.taskService = taskService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/comments")
    public String indexComments(Model model) {
        List<Comment> commentList = commentService.getAllComments();
        model.addAttribute("comments", commentList);
        return "views/Comments/Comment_Index";
    }

    @GetMapping("/roles")
    public String indexRoles(Model model) {
        List<Role> roleList = roleService.getAllRoles();
        model.addAttribute("roles", roleList);
        return "views/Roles/Role_Index";
    }

    @GetMapping("/tasks")
    public String indexTasks(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks",taskList);
        return "views/Tasks/Task_Index";
    }

    @GetMapping("/users")
    public String index(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "views/Users/User_Index";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }
}
