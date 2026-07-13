package com.taskboard.kanban_backend.service;
import com.taskboard.kanban_backend.model.Task;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// @Service tells Spring Boot that this class handles our business logic
// and should be managed automatically by the framework.
@Service
public class TaskService {
    // A temporary in-memory list to store our tasks while the server runs
    private final List<Task> tasks=new ArrayList<>();
    //method to return all tasks currently on the board
    public List<Task> getAllTasks(){
        return tasks;
    }
    //method to create a new task and add it to our list
    public Task createTask(Task task){
        // UUID.randomUUID().toString() automatically generates a unique string (e.g., "d3b07384d...")
        // This ensures every task gets a completely unique ID, just like a real database would do.
        task.setId(UUID.randomUUID().toString());
        //default new tasks to "to_do" if no status is provided
        if(task.getStatus()==null){
            task.setStatus("TO_DO");
        }
        tasks.add(task);
        return task;
    }
}
