package br.com.jtech.tasklist.controllers;

import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.dto.TaskDTO;
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
    public ResponseEntity<Task> create(@RequestBody @Valid TaskDTO taskDTO) {
            Task created = this.taskService.create(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
     @GetMapping
    public List<Task> findAll(){
        return taskService.findAll();
     }
     @GetMapping("/{id}")
     public  ResponseEntity <Task> findById(@PathVariable("id") Long id){
         return ResponseEntity.ok(taskService.findById(id));


     }
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable("id") Long id,@RequestBody @Valid TaskDTO taskDTO){
       Task taskAtualizada = this.taskService.update(id,taskDTO);

        return ResponseEntity.ok(taskAtualizada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    }

