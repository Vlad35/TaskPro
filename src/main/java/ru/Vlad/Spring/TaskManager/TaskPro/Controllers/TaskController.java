package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.TaskDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.TaskService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    private final ModelMapper modelMapper;
    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String index(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks",taskList);
        return "views/Tasks/Task_Index";
    }

    @GetMapping("/create")
    public String newTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task",task);
        return "views/Tasks/Task_Create";
    }

    @PostMapping("/create")
    public String newTask(@ModelAttribute TaskDTO taskDTO) {
        Task task = this.convertToTask(taskDTO);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String showTask(@PathVariable("id") long id,Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if(optionalTask.isPresent()) {
            model.addAttribute("task",optionalTask.get());
            return "views/Tasks/Task_Show";
        }
        return "views/Tasks/TaskNotFound";
    }

    @GetMapping("/edit/{id}")
    public String editTaskPage(@PathVariable("id") long id,Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if(optionalTask.isPresent()) {
            model.addAttribute("task",optionalTask.get());
            return "views/Tasks/Task_Edit";
        }
        return "views/Tasks/TaskNotFound";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable("id") long id,@ModelAttribute TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            modelMapper.map(taskDTO,task);

            taskService.updateTask(task);

            return "redirect:/tasks";
        }
        return "views/Tasks/TaskNotFound";
    }

    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO,Task.class);
    }
}
