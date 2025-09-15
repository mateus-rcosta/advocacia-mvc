package com.unifil.advocacia.gerenciador.contrato.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

public record PostContrato(
        @NotBlank 
        String titulo,

        String descricao,

        @NotNull 
        Long clienteId,

        @NotNull 
        LocalDateTime dataInicio,

        @NotNull 
        LocalDateTime dataFim,
        
        @Length(max = 20) 
        String tipo
    ) {
}
