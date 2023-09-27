package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.TaskDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.TaskService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping
    public String getTasksForUser(@PathVariable("id") long id,Model model) {
        List<Task> taskList = taskService.getTasksByUserId(id);
        model.addAttribute("tasks",taskList);
        return "views/Tasks/Task_Index";
    }

    @GetMapping("/create")
    public String newTaskPage(@PathVariable("id") long id, Model model) {
        Task task = new Task();
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(task::setUser);
        model.addAttribute("task",task);
        model.addAttribute("user",user.get());
        return "views/Tasks/Task_Create";
    }

    @PostMapping("/create")
    public String newTask(@PathVariable("id") long id,@ModelAttribute TaskDTO taskDTO) {
        Task task = this.convertToTask(taskDTO);
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(task::setUser);
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
    public String editTaskPage(@PathVariable("taskId") long id,Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        Optional<User> optionalUser = userService.getUserById(id);
        if(optionalTask.isPresent()  && optionalUser.isPresent() && optionalTask.get().getUser().getName().equals(optionalUser.get().getName())) {
            model.addAttribute("user",optionalUser.get());
            model.addAttribute("task",optionalTask.get());
            return "views/Tasks/Task_Edit";
        }
        return "views/Tasks/TaskNotFound";
    }

    @PostMapping("/edit/{taskId}")
    public String editTask(@PathVariable("id") long id,@PathVariable("taskId") long taskId,@ModelAttribute TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            System.out.println(task.toString());
            modelMapper.map(taskDTO,task);
            System.out.println(task.toString());

            taskService.updateTask(task);

            return "redirect:/" + id + "/tasks";
        }
        return "views/Tasks/TaskNotFound";
    }

    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO,Task.class);
    }
}
