package br.com.jtech.tasklist.service;

import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.exceptions.RegraNegocioException;
import br.com.jtech.tasklist.exceptions.TarefaNaoEncontradaException;
import br.com.jtech.tasklist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

public List<Task> findAll(){
        return taskRepository.findAll();
}

    public Task findById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa com o id: " + id + " não foi encontrado!"));

    }
    public Task create(Task task){
    task.setStatus("pendente");

        if (taskRepository.existsByTitleAndStatus(task.getTitle(), "pendente")) {
            throw new RegraNegocioException(
              String.format("Já existe uma tarefa com o título '%s' em status pendente.",task.getTitle())
            );

        }
        return taskRepository.save(task);
    }
    public Task update(Long id, Task updatedTask){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com o ID: " + id));
    task.setTitle(updatedTask.getTitle());
    task.setDescription(updatedTask.getDescription());
    task.setStatus(updatedTask.getStatus());
    return taskRepository.save(task);

    }
public void delete (Long id){
    taskRepository.deleteById(id);
}

}
