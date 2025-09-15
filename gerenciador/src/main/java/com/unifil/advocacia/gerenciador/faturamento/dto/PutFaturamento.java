package com.unifil.advocacia.gerenciador.faturamento.dto;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.unifil.advocacia.gerenciador.faturamento.enums.StatusFaturamento;

public record PutFaturamento(
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal valor,

        LocalDateTime dataPagamento,

        StatusFaturamento status) {
}