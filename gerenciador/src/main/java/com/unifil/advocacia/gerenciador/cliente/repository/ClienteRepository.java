package com.unifil.advocacia.gerenciador.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifil.advocacia.gerenciador.cliente.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
