package com.taskboard.kanban_backend.controller;

import com.taskboard.kanban_backend.model.Task;
import com.taskboard.kanban_backend.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Allows any frontend application to connect to our API
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
    //3.update an existing task
   // Accessible via HTTP PUT at: http://localhost:8080/api/tasks/{id}
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }
    //delete a task
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return "Task with ID " + id + " was successfully deleted.";
    }

}