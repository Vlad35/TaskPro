package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.CommentDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.CommentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public String newCommentPage(Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "views/Comments/Comment_Create";
    }

    @PostMapping("/create")
    public String newComment(@ModelAttribute CommentDTO commentDTO) {
        Comment comment = convertToComment(commentDTO);
        commentService.createComment(comment);
        return "redirect:/comments";
    }

    @GetMapping("/{id}")
    public String showComment(@PathVariable("id") long id, Model model) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            model.addAttribute("comment", comment.get());
            return "views/Comments/Comment_Show";
        } else {
            return "views/Comments/CommentNotFound";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCommentPage(@PathVariable("id") long id, Model model) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            model.addAttribute("comment", comment.get());
            return "views/Comments/Comment_Edit";
        }
        return "views/Comments/CommentNotFound";
    }

    @PostMapping("/edit/{id}")
    public String editComment(@PathVariable("id") long id, @ModelAttribute CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentService.getCommentById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            modelMapper.map(commentDTO, comment);
            commentService.updateComment(comment);

            return "redirect:/comments";
        }
        return "views/Comments/CommentNotFound";
    }

    private Comment convertToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
