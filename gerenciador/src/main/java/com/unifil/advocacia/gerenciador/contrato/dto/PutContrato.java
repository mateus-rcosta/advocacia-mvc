package com.unifil.advocacia.gerenciador.contrato.dto;

import java.time.LocalDateTime;

import com.unifil.advocacia.gerenciador.contrato.enums.Status;

import jakarta.validation.constraints.Max;

public record PutContrato(
        String titulo,

        String descricao,

        LocalDateTime dataInicio,

        LocalDateTime dataFim,

        Long funcionarioId,

        Status status,
        
        @Max(20) 
        String tipo
    ) {
}
