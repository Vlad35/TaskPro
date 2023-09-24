package ru.Vlad.Spring.TaskManager.TaskPro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Vlad.Spring.TaskManager.TaskPro.Models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
