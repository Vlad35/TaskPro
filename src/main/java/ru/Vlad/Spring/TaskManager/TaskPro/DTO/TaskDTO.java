package ru.Vlad.Spring.TaskManager.TaskPro.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private String status;
    private String priority;
}
