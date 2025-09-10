package br.com.jtech.tasklist.service;

import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.exceptions.RegraNegocioException;
import br.com.jtech.tasklist.exceptions.TarefaNaoEncontradaException;
import br.com.jtech.tasklist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    @BeforeEach
    void setup() {
        task = new Task();
        task.setTitle("Minha Tarefa");
        task.setDescription("Descrição da tarefa");
        task.setStatus("pendente");

    }

    @Test
    void createTask() {
        // Simula que não existe tarefa com mesmo título e status pendente
        when(taskRepository.existsByTitleAndStatus(task.getTitle(), "pendente")).thenReturn(false);
        // Simula salvar e retornar a tarefa
        when(taskRepository.save(task)).thenReturn(task);
        Task created = taskService.create(task);
        assertNotNull(created);
        assertEquals("pendente", created.getStatus());
        verify(taskRepository).save(task);
    }
    @Test
    void createTask_throwsRegraNegocioException_whenDuplicateTitleAndStatus() {
        when(taskRepository.existsByTitleAndStatus(task.getTitle(), "pendente")).thenReturn(true);

        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> {
            taskService.create(task);
        });

        assertTrue(ex.getMessage().contains("Já existe uma tarefa com o título"));
        verify(taskRepository, never()).save(any());
    }
    @Test
    void TaskfindById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task found = taskService.findById(1L);
        assertNotNull(found);
        assertEquals("Minha Tarefa", found.getTitle());
    }
    @Test
    void findById_throwsTarefaNaoEncontradaException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> taskService.findById(99L));
    }

    @Test
    void updateTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Tarefa Atualizada");
        updatedTask.setDescription("Descrição atualizada");
        updatedTask.setStatus("concluída");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.update(1L, updatedTask);

        assertEquals("Tarefa Atualizada", result.getTitle());
        assertEquals("concluída", result.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_throwsTarefaNaoEncontradaException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        Task updatedTask = new Task();
        updatedTask.setTitle("Tarefa Atualizada");
        assertThrows(TarefaNaoEncontradaException.class, () -> taskService.update(99L, updatedTask));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.delete(1L);
        verify(taskRepository).deleteById(1L);
    }
}
