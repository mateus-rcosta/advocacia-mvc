package com.unifil.advocacia.gerenciador.processo.dto;

import java.time.LocalDateTime;

import com.unifil.advocacia.gerenciador.processo.enums.StatusProcesso;
import com.unifil.advocacia.gerenciador.processo.enums.TipoProcesso;

public record PutProcesso(
        String numero,
        TipoProcesso tipo,
        String descricao,
        LocalDateTime dataAbertura,
        LocalDateTime dataEncerramento,
        StatusProcesso statusProcesso
) {
}