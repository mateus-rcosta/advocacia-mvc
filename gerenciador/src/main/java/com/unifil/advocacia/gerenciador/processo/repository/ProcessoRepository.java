package com.unifil.advocacia.gerenciador.processo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifil.advocacia.gerenciador.processo.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long>{
    
}
