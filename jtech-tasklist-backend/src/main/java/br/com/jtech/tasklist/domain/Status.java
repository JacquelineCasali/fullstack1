package br.com.jtech.tasklist.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PENDENTE,
    CONCLUIDA;
    @JsonCreator
    public static Status from(String value) {
        return Status.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();

    }
}
