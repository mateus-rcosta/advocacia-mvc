package com.unifil.advocacia.gerenciador.documento.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.unifil.advocacia.gerenciador.processo.model.Processo;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeOriginal;
    private String nomeArmazenado;
    private String caminho;
    private String tipoMime;
    private LocalDateTime dataUpload;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    // Construtores
    public Documento() {}

    public Documento(String nomeOriginal, String nomeArmazenado, String caminho, String tipoMime, LocalDateTime dataUpload) {
        this.nomeOriginal = nomeOriginal;
        this.nomeArmazenado = nomeArmazenado;
        this.caminho = caminho;
        this.tipoMime = tipoMime;
        this.dataUpload = dataUpload;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeOriginal() { return nomeOriginal; }
    public void setNomeOriginal(String nomeOriginal) { this.nomeOriginal = nomeOriginal; }

    public String getNomeArmazenado() { return nomeArmazenado; }
    public void setNomeArmazenado(String nomeArmazenado) { this.nomeArmazenado = nomeArmazenado; }

    public String getCaminho() { return caminho; }
    public void setCaminho(String caminho) { this.caminho = caminho; }

    public String getTipoMime() { return tipoMime; }
    public void setTipoMime(String tipoMime) { this.tipoMime = tipoMime; }

    public LocalDateTime getDataUpload() { return dataUpload; }
    public void setDataUpload(LocalDateTime dataUpload) { this.dataUpload = dataUpload; }

    public Processo getProcesso() { return processo; }

    public void setProcesso(Processo processo) { this.processo = processo; }

}
