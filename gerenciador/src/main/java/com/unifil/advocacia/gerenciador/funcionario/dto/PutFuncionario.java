package com.unifil.advocacia.gerenciador.funcionario.dto;

import com.unifil.advocacia.gerenciador.funcionario.enums.Funcao;

public record PutFuncionario(
    String nome,
    String email,
    String senha,
    Funcao funcao
) {
    
}