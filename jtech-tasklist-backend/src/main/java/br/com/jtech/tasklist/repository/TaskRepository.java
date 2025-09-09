package br.com.jtech.tasklist.repository;


import br.com.jtech.tasklist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TaskRepository extends JpaRepository<Task, Long> {
// se existir tarefa com o mesmo titulo e status pendente não criar
    boolean existsByTituloAndStatus(String titulo, String status);
}