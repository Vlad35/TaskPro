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
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        role1.setDescription("Администратор");
        roleService.createRole(role1);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        role2.setDescription("Пользователь");
        roleService.createRole(role2);

        User user1 = new User();
        user1.setName("Пользователь 1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        Set<Role> user1Roles = new HashSet<>();
        user1Roles.add(role1);
        user1Roles.add(role2);
        user1.setRoles(user1Roles);
        userService.createUser(user1);

        User user2 = new User();
        user2.setName("Пользователь 2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        Set<Role> user2Roles = new HashSet<>();
        user2Roles.add(role2);
        user2.setRoles(user2Roles);
        userService.createUser(user2);

        Task task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setCreatedAt(LocalDateTime.now());
        task1.setStatus("в процессе");
        task1.setPriority("высокий");
        task1.setUser(user1);
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setCreatedAt(LocalDateTime.now());
        task2.setStatus("завершено");
        task2.setPriority("средний");
        task2.setUser(user2);
        taskService.createTask(task2);

        Comment comment1 = new Comment();
        comment1.setBody("Комментарий 1");
        comment1.setCreatedAt(LocalDateTime.now());
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        comment2.setBody("Комментарий 2");
        comment2.setCreatedAt(LocalDateTime.now());
        comment2.setUser(user2);

        comment1.setTask(task1);
        comment2.setTask(task2);

        commentService.createComment(comment1);
        commentService.createComment(comment2);
    }
}
