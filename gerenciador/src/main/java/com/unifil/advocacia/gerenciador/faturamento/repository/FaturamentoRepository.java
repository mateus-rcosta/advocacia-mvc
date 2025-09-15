package com.unifil.advocacia.gerenciador.faturamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifil.advocacia.gerenciador.faturamento.model.Faturamento;

public interface  FaturamentoRepository extends JpaRepository<Faturamento, Long>{
    
}
