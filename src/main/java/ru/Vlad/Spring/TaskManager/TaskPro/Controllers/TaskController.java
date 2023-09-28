package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.TaskDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Security.MyUserDetails;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.TaskService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/{id}/tasks")
public class TaskController {
    private final TaskService taskService;

    private final UserService userService;

    private final ModelMapper modelMapper;
    @Autowired
    public TaskController(TaskService taskService, UserService userService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private boolean isAccessed(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();
        return user.getId() == id;
    }

    @GetMapping
    public String getTasksForUser(@PathVariable("id") long id,Model model) {
        boolean isAccessed = this.isAccessed(id);
        if(isAccessed) {
            List<Task> taskList = taskService.getTasksByUserId(id);
            model.addAttribute("tasks",taskList);
            return "views/Tasks/Task_Index";
        }
        return "views/Auth/Access_Denied";
    }

    @GetMapping("/create")
    public String newTaskPage(@PathVariable("id") long id, Model model) {
        if(isAccessed(id)) {
            Task task = new Task();
            Optional<User> user = userService.getUserById(id);
            user.ifPresent(task::setUser);
            model.addAttribute("task",task);
            model.addAttribute("user",user.get());
            return "views/Tasks/Task_Create";
        }
        return "views/Auth/Access_Denied";
    }

    @PostMapping("/create")
    public String newTask(@PathVariable("id") long id,@ModelAttribute TaskDTO taskDTO) {
        Task task = this.convertToTask(taskDTO);
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(task::setUser);
        user.get().getTasks().add(task);
        task.setCreatedAt(LocalDateTime.now());
        taskService.createTask(task);
        return "redirect:/" + id + "/tasks";
    }

    @GetMapping("/{taskId}")
    public String showTask(@PathVariable("taskId") long taskId,@PathVariable("id") long id, Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        Optional<User> optionalUser = userService.getUserById(id);
        if(optionalTask.isPresent() && optionalUser.isPresent() && optionalTask.get().getUser().getName().equals(optionalUser.get().getName())) {
            model.addAttribute("task",optionalTask.get());
            return "views/Tasks/Task_Show";
        }
        return "views/Tasks/TaskNotFound";
    }

    @GetMapping("/edit/{taskId}")
    public String editTaskPage(@PathVariable("id") long id, @PathVariable("taskId") long taskId, Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        Optional<User> optionalUser = userService.getUserById(id);

        if(optionalTask.isPresent() && optionalUser.isPresent() && isAccessed(optionalTask.get().getUser().getId()) && Objects.equals(optionalUser.get().getId(), optionalTask.get().getUser().getId())) {
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("task", optionalTask.get());
            return "views/Tasks/Task_Edit";
        }
        return "views/Auth/Access_Denied";
    }

    @PostMapping("/edit/{taskId}")
    public String editTask(@PathVariable("id") long id, @PathVariable("taskId") long taskId, @ModelAttribute TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);

        if (optionalTask.isPresent() && optionalTask.get().getUser().getId() == id) {
            Task task = optionalTask.get();
            modelMapper.map(taskDTO, task);
            taskService.updateTask(task);
            return "redirect:/" + id + "/tasks";
        } else {
            return "views/Auth/Access_Denied";
        }
    }


    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO,Task.class);
    }
}
