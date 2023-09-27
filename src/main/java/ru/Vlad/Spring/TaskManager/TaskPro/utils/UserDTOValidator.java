package ru.Vlad.Spring.TaskManager.TaskPro.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Vlad.Spring.TaskManager.TaskPro.DTO.UserDTO;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Services.UserService;

@Component
public class UserDTOValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserDTOValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if(!userService.getUserByName(userDTO.getName()).isPresent()) {
           return;
        }
        errors.rejectValue("username","","Nickname is used by Someone else");
    }
}
