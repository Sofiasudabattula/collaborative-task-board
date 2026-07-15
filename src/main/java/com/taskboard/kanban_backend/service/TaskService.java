package com.taskboard.kanban_backend.service;
import com.taskboard.kanban_backend.model.Task;
import com.taskboard.kanban_backend.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
// @Service tells Spring Boot that this class handles our business logic
// and should be managed automatically by the framework.
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    //method to create a new task and add it to our list
    public Task createTask(Task task){
        // Generate a unique identifier if one isn't present
        if(task.getId()==null){
            task.setId(UUID.randomUUID().toString());
        }
        // Default new tasks to "TO_DO"
        if(task.getStatus()==null){
            task.setStatus("TO_DO");
        }
        // Save to PostgreSQL and return the saved entity
        return taskRepository.save(task);
    }
}
