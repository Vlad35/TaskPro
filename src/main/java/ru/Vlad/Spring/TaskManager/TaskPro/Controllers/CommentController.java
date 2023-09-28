package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.CommentDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Security.MyUserDetails;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.CommentService;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/{userId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private boolean isAccessed(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();
        return user.getId() == userId;
    }

    @GetMapping
    public String getCommentsForUser(@PathVariable("userId") long userId, Model model) {
        boolean isAccessed = this.isAccessed(userId);
        if (isAccessed) {
            // Логика получения комментариев для пользователя
            List<Comment> comments = commentService.getCommentsByUserId(userId);
            model.addAttribute("comments", comments);
            return "views/Comments/Comment_Index";
        }
        return "views/Auth/Access_Denied";
    }

    @GetMapping("/create")
    public String newCommentPage(@PathVariable("userId") long userId, Model model) {
        if (isAccessed(userId)) {
            Comment comment = new Comment();
            model.addAttribute("comment", comment);
            model.addAttribute("userId",userId);
            return "views/Comments/Comment_Create";
        }
        return "views/Auth/Access_Denied";
    }

    @PostMapping("/create")
    public String newComment(@PathVariable("userId") long userId, @ModelAttribute CommentDTO commentDTO) {
        if (isAccessed(userId)) {
            Comment comment = convertToComment(commentDTO);
            commentService.createComment(comment);
            return "redirect:/" + userId + "/comments";
        }
        return "views/Auth/Access_Denied";
    }

    @GetMapping("/{commentId}")
    public String showComment(@PathVariable("userId") long userId, @PathVariable("commentId") long commentId, Model model) {
        boolean isAccessed = this.isAccessed(userId);
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (isAccessed && comment.isPresent()) {
            model.addAttribute("comment", comment.get());
            return "views/Comments/Comment_Show";
        } else {
            return "views/Comments/CommentNotFound";
        }
    }

    @GetMapping("/edit/{commentId}")
    public String editCommentPage(@PathVariable("userId") long userId, @PathVariable("commentId") long commentId, Model model) {
        boolean isAccessed = this.isAccessed(userId);
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (isAccessed && comment.isPresent()) {
            model.addAttribute("comment", comment.get());
            return "views/Comments/Comment_Edit";
        }
        return "views/Comments/CommentNotFound";
    }

    @PostMapping("/edit/{commentId}")
    public String editComment(@PathVariable("userId") long userId, @PathVariable("commentId") long commentId, @ModelAttribute CommentDTO commentDTO) {
        boolean isAccessed = this.isAccessed(userId);
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        if (isAccessed && optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            modelMapper.map(commentDTO, comment);
            commentService.updateComment(comment);
            return "redirect:/" + userId + "/comments";
        }
        return "views/Comments/CommentNotFound";
    }

    private Comment convertToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
