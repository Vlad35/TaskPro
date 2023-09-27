package ru.Vlad.Spring.TaskManager.TaskPro.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.User;
import ru.Vlad.Spring.TaskManager.TaskPro.Repositories.RoleRepository;
import ru.Vlad.Spring.TaskManager.TaskPro.Repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");

        if(optionalRole.isPresent()) {
            Role role = optionalRole.get();
            if (user.getRoles().stream().map(Role::getName).toList().contains(role.getName())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            } else {
                user.getRoles().add(role);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        return userRepository.save(user);
    }



    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }



}
