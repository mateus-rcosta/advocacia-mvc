package com.unifil.advocacia.gerenciador.documento.controller;

import com.unifil.advocacia.gerenciador.documento.model.Documento;
import com.unifil.advocacia.gerenciador.documento.service.DocumentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public String listar(Model model) {
        List<Documento> documentos = documentoService.listar();
        model.addAttribute("documentos", documentos);
        return "documentos/listar";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                        @RequestParam("processoId") Long processoId,
                        RedirectAttributes redirect) {
        try {
            documentoService.salvar(file, processoId);
            redirect.addFlashAttribute("mensagem", "Documento enviado com sucesso!");
        } catch (IOException e) {
            redirect.addFlashAttribute("mensagem", "Erro ao enviar documento!");
        }
        return "redirect:/documentos/por-processo/" + processoId;
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id, HttpServletRequest request) throws IOException {
        Resource resource = documentoService.carregar(id);

        String contentType = Files.probeContentType(resource.getFile().toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            documentoService.excluir(id);
            redirect.addFlashAttribute("mensagem", "Documento excluído com sucesso!");
        } catch (IOException e) {
            redirect.addFlashAttribute("mensagem", "Erro ao excluir documento!");
        }
        return "redirect:/documentos";
    }

    @GetMapping("/por-processo/{processoId}")
    public String listarPorProcesso(@PathVariable Long processoId, Model model) {
        List<Documento> documentos = documentoService.listarPorProcesso(processoId);
        model.addAttribute("documentos", documentos);
        model.addAttribute("processoId", processoId);
        return "documentos/listar";
}

}
