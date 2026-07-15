package com.taskboard.kanban_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    private String id;

    private String title;
    private String description;
    private String status;
}