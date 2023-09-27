package ru.Vlad.Spring.TaskManager.TaskPro.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="Comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @Column(name="body")
    private String body;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "task_id",referencedColumnName = "Id")
    private Task task;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "Id")
    private User user;
}
