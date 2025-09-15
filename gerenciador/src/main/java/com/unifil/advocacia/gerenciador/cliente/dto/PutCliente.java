package com.unifil.advocacia.gerenciador.cliente.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;

public record PutCliente(
    String nome,

    LocalDate dataNascimento,

    String cpf,

    String endereco,

    String telefone,
    
    @Email
    String email
) {
    
}