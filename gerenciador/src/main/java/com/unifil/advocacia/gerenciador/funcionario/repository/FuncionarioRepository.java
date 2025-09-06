package com.unifil.advocacia.gerenciador.funcionario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    boolean existsByEmail(String email);
    Optional<Funcionario> findByEmail(String email);
}