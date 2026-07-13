package com.taskboard.kanban_backend.model;

import lombok.Data;

// @Data is a magical Lombok annotation.
// It automatically generates getters, setters, equals(), hashCode(),
// and a toString() method behind the scenes so we don't have to write 100 lines of boilerplate code.
@Data
public class Task {

    private String id;
    private String title;
    private String description;
    private String status; // Will hold values like "TO_DO", "IN_PROGRESS", "DONE"
}