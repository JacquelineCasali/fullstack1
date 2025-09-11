package br.com.jtech.tasklist.service;

import br.com.jtech.tasklist.domain.Status;
import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.dto.TaskDTO;
import br.com.jtech.tasklist.infra.exceptions.RegraNegocioException;
import br.com.jtech.tasklist.infra.exceptions.TarefaNaoEncontradaException;
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
    public Task create(TaskDTO taskDTO){
        if (taskRepository.existsByTitleAndStatus(taskDTO.getTitle(), Status.PENDENTE)) {
            throw new RegraNegocioException(
              String.format("Já existe uma tarefa com o título '%s' em status pendente.",taskDTO.getTitle())
            );
        }
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(Status.from(taskDTO.getStatus()));
        return taskRepository.save(task);
    }
    public Task update(Long id, TaskDTO taskDTO){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com o ID: " + id));
        // Verifica se existe outra tarefa pendente com o mesmo título
        boolean exists = taskRepository.existsByTitleAndStatusAndIdNot(
                taskDTO.getTitle(), Status.PENDENTE, id);
        if (exists) {
            throw new RegraNegocioException(
                    "Já existe uma outra tarefa pendente com esse título.");
        }
    task.setTitle(taskDTO.getTitle());
    task.setDescription(taskDTO.getDescription());
    task.setStatus(Status.from(taskDTO.getStatus()));
    return taskRepository.save(task);

    }
public void delete (Long id){
    taskRepository.deleteById(id);
}

}
