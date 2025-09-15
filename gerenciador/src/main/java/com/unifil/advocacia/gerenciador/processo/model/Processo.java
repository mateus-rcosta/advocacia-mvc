package com.unifil.advocacia.gerenciador.processo.model;

import com.unifil.advocacia.gerenciador.contrato.model.Contrato;
import com.unifil.advocacia.gerenciador.processo.enums.StatusProcesso;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String numero; 

    @Column(nullable = false, length = 200)
    private String tipo; 

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProcesso status = StatusProcesso.EM_ANDAMENTO; 

    @Column(nullable = false)
    private LocalDateTime dataAbertura; 

    @Column
    private LocalDateTime dataEncerramento; 

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
