package com.unifil.advocacia.gerenciador.cliente.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCliente(
    @NotNull @NotBlank
    String nome,

    @NotNull
    LocalDate dataNascimento,

    @NotNull @NotBlank
    String cpf,

    @NotNull @NotBlank
    String endereco,

    @NotNull @NotBlank
    String telefone,

    @NotNull @NotBlank @Email
    String email
) {
    
}
