package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String index() {
        return "views/Home";
    }
}
