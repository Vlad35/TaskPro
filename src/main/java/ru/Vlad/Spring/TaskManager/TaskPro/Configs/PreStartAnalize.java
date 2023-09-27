package ru.Vlad.Spring.TaskManager.TaskPro.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.CommentService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.RoleService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.TaskService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class PreStartAnalize implements CommandLineRunner {
    private final TaskService taskService;
    private final UserService userService;
    private final RoleService roleService;
    private final CommentService commentService;

    @Autowired
    public PreStartAnalize(TaskService taskService, UserService userService, RoleService roleService, CommentService commentService) {
        this.taskService = taskService;
        this.userService = userService;
        this.roleService = roleService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setName("user 1");
        user1.setPassword("pass");
        userService.createUser(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setName("user 2");
        user2.setPassword("pass");
        userService.createUser(user2);

        User user3 = new User();
        user3.setEmail("user3@example.com");
        user3.setName("user 3");
        user3.setPassword("pass");
        userService.createUser(user3);

    }
}
