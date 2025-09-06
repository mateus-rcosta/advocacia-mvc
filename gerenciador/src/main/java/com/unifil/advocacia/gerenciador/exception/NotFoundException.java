package com.unifil.advocacia.gerenciador.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private Map<String, Boolean> recurso;

    public NotFoundException(String message, Map<String, Boolean> recurso){
        super(message);
        this.recurso = recurso;
    }
}