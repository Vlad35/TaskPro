package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.CommentService;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public String index(Model model) {
        List<Comment> commentList = commentService.getAllComments();
        model.addAttribute("comments", commentList);
        return "views/Comment_Index";
    }
}
