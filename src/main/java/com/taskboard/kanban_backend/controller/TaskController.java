package com.taskboard.kanban_backend.controller;

import com.taskboard.kanban_backend.model.Task;
import com.taskboard.kanban_backend.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController marks this as a web controller returning data.
// @RequestMapping("/api/tasks") means every route in this file automatically starts with "/api/tasks".
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor Injection: Spring Boot automatically gives this controller access to our TaskService.
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. Get all tasks on the board
    // Accessible via HTTP GET at: http://localhost:8080/api/tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // 2. Create a new task
    // Accessible via HTTP POST at: http://localhost:8080/api/tasks
    // @RequestBody tells Spring to convert incoming JSON data from the browser directly into a Java Task object.
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
}