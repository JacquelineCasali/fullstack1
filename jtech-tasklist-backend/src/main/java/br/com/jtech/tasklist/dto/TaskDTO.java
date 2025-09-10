package br.com.jtech.tasklist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTO {
    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.")
    private String title;

    private String description;
    @NotNull(message = "O status é obrigatório.")
    private String status;
}
