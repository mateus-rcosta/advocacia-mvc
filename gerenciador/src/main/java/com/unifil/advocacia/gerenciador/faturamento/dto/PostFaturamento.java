package com.unifil.advocacia.gerenciador.faturamento.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PostFaturamento(
    @NotNull()
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal valor,

    @NotNull()
    LocalDateTime dataEmissao,

    @NotNull()
    Long contratoId
) {}