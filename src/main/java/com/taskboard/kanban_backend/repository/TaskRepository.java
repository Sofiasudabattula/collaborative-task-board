package com.taskboard.kanban_backend.repository;
import com.taskboard.kanban_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Extending JpaRepository automatically gives us methods like:
// save(), findAll(), findById(), deleteById() without writing a single line of implementation!
@Repository
public interface TaskRepository extends JpaRepository<Task, String>{
    // the string type represents data type of entity's ID field.
}
