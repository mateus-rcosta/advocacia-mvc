package com.unifil.advocacia.gerenciador.processo.dto;

import java.time.LocalDateTime;

import com.unifil.advocacia.gerenciador.processo.enums.TipoProcesso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostProcesso(
        @NotNull @NotBlank
        String numero,
        @NotNull
        TipoProcesso tipo,
        String descricao,
        @NotNull
        LocalDateTime dataAbertura,
        @NotNull
        Long contratoId
) {
}
