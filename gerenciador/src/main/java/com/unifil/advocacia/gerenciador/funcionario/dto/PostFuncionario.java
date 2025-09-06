package com.unifil.advocacia.gerenciador.funcionario.dto;

import com.unifil.advocacia.gerenciador.funcionario.enums.Funcao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostFuncionario(
    @NotBlank @NotNull
    String nome,
    @NotBlank @NotNull @Email
    String email,
    @NotBlank @NotNull 
    String senha,
    @NotBlank @NotNull 
    Funcao funcao
) {
} 