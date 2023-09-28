package ru.Vlad.Spring.TaskManager.TaskPro.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Security.MyUserDetails;

@Controller
public class HomeController {
    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        if(getCurrentSessionUser() == 0) {
            model.addAttribute("isAdmin",true);
        }else {
            model.addAttribute("isAdmin",false);
        }
        model.addAttribute("user",myUserDetails.getUser());
        return "views/Home";
    }

    @GetMapping("/showUserInfo")
    public String showCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("user",myUserDetails.getUser());
        return "views/Users/User_Show";
    }

    private int getCurrentSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return 0;
        }
        return 1;
    }
}
