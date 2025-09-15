package com.unifil.advocacia.gerenciador.processo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unifil.advocacia.gerenciador.contrato.service.ContratoService;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.processo.dto.PostProcesso;
import com.unifil.advocacia.gerenciador.processo.dto.PutProcesso;
import com.unifil.advocacia.gerenciador.processo.model.Processo;
import com.unifil.advocacia.gerenciador.processo.service.ProcessoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/processos")
@Validated
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;
    @Autowired
    private ContratoService contratoService;

    // Listar processos
    @GetMapping
    public String listarProcessos(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        List<Processo> processos = processoService.listarProcessos();
        model.addAttribute("processos", processos);
        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "processos/listar";
    }

    // Exibir formulário de criação
    @GetMapping("/novo")
    public String novoProcessoForm(Model model) {
        if (!model.containsAttribute("processo")) {
            model.addAttribute("processo", new PostProcesso("", null, "", null, null));
        }
        model.addAttribute("contratos", contratoService.listarContratos());
        return "processos/form";
    }

    // Criar processo
    @PostMapping
    public String salvarProcesso(@ModelAttribute @Valid PostProcesso processo, Model model) {
        try {
            processoService.criarProcesso(processo);
            return "redirect:/processos";
        } catch (BadRequestException | NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("processo", processo);
            return "processos/form";
        }
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarProcessoForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Processo processo = processoService.buscarProcessoPorId(id);
            model.addAttribute("processo", processo);
            model.addAttribute("contratos", contratoService.listarContratos()); 
            return "processos/editar";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/processos";
        }
    }

    // Atualizar processo
    @PostMapping("/editar/{id}")
    public String atualizarProcesso(@PathVariable Long id, @ModelAttribute @Valid PutProcesso processo, Model model) {
        try {
            processoService.atualizarProcesso(id, processo);
            return "redirect:/processos";
        } catch (NotFoundException | BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("processo", processo);
            return "processos/editar";
        }
    }

    // Excluir processo
    @GetMapping("/excluir/{id}")
    public String excluirProcesso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            processoService.excluirProcesso(id);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/processos";
    }
}