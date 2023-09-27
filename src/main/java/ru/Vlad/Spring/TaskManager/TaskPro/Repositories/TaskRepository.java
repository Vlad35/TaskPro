package ru.Vlad.Spring.TaskManager.TaskPro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long userId);
}
