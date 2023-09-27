package ru.Vlad.Spring.TaskManager.TaskPro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findByDescription(String desc);
}
