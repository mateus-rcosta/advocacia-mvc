package com.unifil.advocacia.gerenciador.faturamento.model;

import com.unifil.advocacia.gerenciador.contrato.model.Contrato;
import com.unifil.advocacia.gerenciador.faturamento.enums.StatusFaturamento;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "faturamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faturamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataEmissao;

    @Column
    private LocalDateTime dataPagamento;

    
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusFaturamento status = StatusFaturamento.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}