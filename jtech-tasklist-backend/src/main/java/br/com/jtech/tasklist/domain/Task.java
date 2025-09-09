package br.com.jtech.tasklist.domain;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(length = 50)
    private String titulo;
    private String descricao;

    private String status; // ex: pendente, concluída

    @CreationTimestamp
    private LocalDateTime createdAt;



    public void setTitulos (String titulo) throws Exception {
        if (titulo.length() > 50) {
            throw new Exception("O campo titulo deve conter no máximo 50 caracteres");
        }
        this.titulo = titulo;
    }

}
