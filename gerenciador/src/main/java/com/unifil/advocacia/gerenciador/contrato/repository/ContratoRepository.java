package com.unifil.advocacia.gerenciador.contrato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unifil.advocacia.gerenciador.contrato.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>{
    
}
