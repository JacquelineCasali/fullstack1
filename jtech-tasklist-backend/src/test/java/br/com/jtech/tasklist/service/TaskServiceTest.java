package br.com.jtech.tasklist.service;

import br.com.jtech.tasklist.domain.Status;
import br.com.jtech.tasklist.domain.Task;
import br.com.jtech.tasklist.dto.TaskDTO;
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
    private TaskDTO taskDTO;
    @BeforeEach
    void setup() {
        taskDTO= new TaskDTO();
        taskDTO.setTitle("Minha Tarefa");
        taskDTO.setDescription("Descrição da tarefa");
        taskDTO.setStatus("PENDENTE");
        task = new Task();
        task.setTitle("Minha Tarefa");
        task.setDescription("Descrição da tarefa");
        task.setStatus(Status.PENDENTE);

    }

    @Test
    void createTask() {
        // Simula que não existe tarefa com mesmo título e status pendente
        when(taskRepository.existsByTitleAndStatus(task.getTitle(), Status.PENDENTE)).thenReturn(false);
        // Simula salvar e retornar a tarefa
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task created = taskService.create(taskDTO);
        assertNotNull(created);
        assertEquals(Status.PENDENTE, created.getStatus());
        verify(taskRepository).save(any(Task.class));
    }
    @Test
    void createTask_throwsRegraNegocioException_whenDuplicateTitleAndStatus() {
        when(taskRepository.existsByTitleAndStatus(task.getTitle(), Status.PENDENTE)).thenReturn(true);

        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> {
            taskService.create(taskDTO);
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
        TaskDTO updateDTO = new TaskDTO();
        updateDTO.setTitle("Tarefa Atualizada");
        updateDTO.setDescription("Descrição atualizada");
        updateDTO.setStatus("concluida");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.existsByTitleAndStatusAndIdNot("Tarefa Atualizada", Status.PENDENTE, 1L)).thenReturn(false);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updated = taskService.update(1L, updateDTO);

        assertNotNull(updated);
        assertEquals("Tarefa Atualizada", updated.getTitle());
        assertEquals(Status.CONCLUIDA, updated.getStatus());
    }

    @Test
    void updateTask_throwsTarefaNaoEncontradaException() {
        TaskDTO updateDTO = new TaskDTO();
        updateDTO.setTitle("Tarefa Nova");
        updateDTO.setStatus("pendente");

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () ->
                taskService.update(99L, updateDTO)
        );

        verify(taskRepository, never()).save(any());
    }

    @Test
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.delete(1L);
        verify(taskRepository).deleteById(1L);
    }
}
