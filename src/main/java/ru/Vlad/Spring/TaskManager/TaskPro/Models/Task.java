package ru.Vlad.Spring.TaskManager.TaskPro.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name="status")
    private String status;

    @Column(name="priority")
    private String priority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "Id")
    private User user;

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", comments=" + comments +
                ", user=" + user +
                '}';
    }
}
