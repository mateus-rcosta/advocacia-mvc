package com.unifil.advocacia.gerenciador.documento.service;

import com.unifil.advocacia.gerenciador.documento.model.Documento;
import com.unifil.advocacia.gerenciador.documento.repository.DocumentoRepository;

import com.unifil.advocacia.gerenciador.processo.model.Processo;
import com.unifil.advocacia.gerenciador.processo.repository.ProcessoRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;




@Service
public class DocumentoService {

    private final Path uploadPath = Paths.get("uploads");

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ProcessoRepository processoRepository;


    public Documento salvar(MultipartFile file, Long processoId) throws IOException {
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    String nomeOriginal = file.getOriginalFilename();
    String nomeArmazenado = UUID.randomUUID() + "_" + nomeOriginal;
    Path destino = uploadPath.resolve(nomeArmazenado);

    Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

    Processo processo = processoRepository.findById(processoId)
            .orElseThrow(() -> new IOException("Processo não encontrado"));

    Documento documento = new Documento();
    documento.setNomeOriginal(nomeOriginal);
    documento.setNomeArmazenado(nomeArmazenado);
    documento.setCaminho(destino.toString());
    documento.setTipoMime(file.getContentType());
    documento.setDataUpload(LocalDateTime.now());
    documento.setProcesso(processo); 

    return documentoRepository.save(documento);

    }

    public Resource carregar(Long id) throws IOException {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new IOException("Documento não encontrado"));

        Path caminho = Paths.get(doc.getCaminho());
        return new UrlResource(caminho.toUri());
    }

    public List<Documento> listar() {
        return documentoRepository.findAll();
    }

    public void excluir(Long id) throws IOException {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new IOException("Documento não encontrado"));

        Files.deleteIfExists(Paths.get(doc.getCaminho()));
        documentoRepository.delete(doc);
    }

    public List<Documento> listarPorProcesso(Long processoId) {
    return documentoRepository.findAll()
            .stream()
            .filter(d -> d.getProcesso() != null && d.getProcesso().getId().equals(processoId))
            .toList();
    }


}
