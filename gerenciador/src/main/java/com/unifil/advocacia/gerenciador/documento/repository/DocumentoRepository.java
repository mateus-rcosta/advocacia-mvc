package com.unifil.advocacia.gerenciador.documento.repository;

import com.unifil.advocacia.gerenciador.documento.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
