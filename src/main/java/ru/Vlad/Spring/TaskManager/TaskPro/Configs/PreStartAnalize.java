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
        // Создаем роли и сохраняем их в базе данных
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        role1.setDescription("CAN EVERYTHING");
        roleService.createRole(role1);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        role2.setDescription("CANNOT EVERYTHING");
        roleService.createRole(role2);

        // Создаем пользователей и сохраняем их в базе данных
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setName("user 1");
        user1.setPassword("pass");
        user1.getRoles().add(role1);
        userService.createUser(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setName("user 2");
        user2.setPassword("pass");
        user2.getRoles().add(role2);
        userService.createUser(user2);

        User user3 = new User();
        user3.setEmail("user3@example.com");
        user3.setName("user 3");
        user3.setPassword("pass");
        userService.createUser(user3);

        // Создаем задачи и сохраняем их в базе данных
        Task task1 = new Task();
        task1.setCreatedAt(LocalDateTime.parse("2023-09-27T08:00:00"));
        task1.setUser(user1);
        task1.setDescription("Создать план завоевания космоса: начать с выбора лучшей звезды и разработки маршрута до нее.");
        task1.setName("Завоевание космоса");
        task1.setPriority("Высокий");
        task1.setStatus("В процессе");
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setCreatedAt(LocalDateTime.parse("2023-09-27T09:30:00"));
        task2.setUser(user2);
        task2.setDescription("Расширить знания о межгалактических червях и исследовать их потенциал для путешествий во времени.");
        task2.setName("Исследование червей");
        task2.setPriority("Средний");
        task2.setStatus("Завершена");
        taskService.createTask(task2);

        Task task3 = new Task();
        task3.setCreatedAt(LocalDateTime.parse("2023-09-27T11:45:00"));
        task3.setUser(user3);
        task3.setDescription("Создать машину времени и вернуться в детство, чтобы исправить ошибки и стать супергероем.");
        task3.setName("Машина времени");
        task3.setPriority("Низкий");
        task3.setStatus("Не начата");
        taskService.createTask(task3);

        // Создаем комментарии и сохраняем их в базе данных
        Comment comment1 = new Comment();
        comment1.setCreatedAt(LocalDateTime.parse("2023-09-27T10:00:00"));
        comment1.setTask(task1);
        comment1.setUser(user1);
        comment1.setBody("Первый комментарий");
        commentService.createComment(comment1);

        Comment comment2 = new Comment();
        comment2.setCreatedAt(LocalDateTime.parse("2023-09-27T11:30:00"));
        comment2.setTask(task2);
        comment2.setUser(user2);
        comment2.setBody("Второй комментарий");
        commentService.createComment(comment2);

        Comment comment3 = new Comment();
        comment3.setCreatedAt(LocalDateTime.parse("2023-09-27T13:45:00"));
        comment3.setTask(task3);
        comment3.setUser(user3);
        comment3.setBody("Третий комментарий");
        commentService.createComment(comment3);
    }
}
