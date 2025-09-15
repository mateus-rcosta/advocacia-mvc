package com.unifil.advocacia.gerenciador.contrato.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.unifil.advocacia.gerenciador.contrato.enums.Status;


public record PutContrato(
        String titulo,

        String descricao,

        LocalDateTime dataInicio,

        LocalDateTime dataFim,

        Long funcionarioId,

        Status status,
        
        @Length(max = 20) 
        String tipo
    ) {
}
