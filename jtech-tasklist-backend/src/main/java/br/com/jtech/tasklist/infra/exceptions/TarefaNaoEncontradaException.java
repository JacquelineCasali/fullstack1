package br.com.jtech.tasklist.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TarefaNaoEncontradaException extends RuntimeException{
    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
