package com.unifil.advocacia.gerenciador.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private Map<String, Boolean> recurso;

    public BadRequestException (String message, Map<String, Boolean> recurso){
        super(message);
        this.recurso = recurso;
    }
}
