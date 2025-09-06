package com.unifil.advocacia.gerenciador.funcionario.model;

import java.time.LocalDateTime;

import com.unifil.advocacia.gerenciador.funcionario.enums.Funcao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "funcionarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;
    
    @Column(name = "funcao", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Funcionario(String nome, String email, String senha, Funcao funcao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}