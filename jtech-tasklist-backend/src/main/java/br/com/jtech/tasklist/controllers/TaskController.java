package br.com.jtech.tasklist.controllers;

import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid Task task) {
            Task created = this.taskService.create(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
     @GetMapping
    public List<Task> findAll(){
        return taskService.findAll();
     }
     @GetMapping("/{id}")
     public Task findById(@PathVariable Long id){
         return taskService.findById(id);
     }
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id,@RequestBody @Valid Task task){
       Task taskAtualizada = this.taskService.update(id,task);

        return ResponseEntity.ok(taskAtualizada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    }

