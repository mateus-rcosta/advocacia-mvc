package com.unifil.advocacia.gerenciador.faturamento.controller;

import com.unifil.advocacia.gerenciador.contrato.service.ContratoService;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.faturamento.dto.PostFaturamento;
import com.unifil.advocacia.gerenciador.faturamento.dto.PutFaturamento;
import com.unifil.advocacia.gerenciador.faturamento.model.Faturamento;
import com.unifil.advocacia.gerenciador.faturamento.service.FaturamentoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/faturamentos")
@Validated
public class FaturamentoController {

    @Autowired
    private FaturamentoService faturamentoService;

    @Autowired
    private ContratoService contratoService;

    // Listar faturamentos
    @GetMapping
    public String listarFaturamentos(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("faturamentos", faturamentoService.listarFaturamentos());
        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "faturamentos/listar";
    }

    // Novo faturamento
    @GetMapping("/novo")
    public String novoFaturamentoForm(Model model) {
        if (!model.containsAttribute("faturamento")) {
            model.addAttribute("faturamento", new PostFaturamento(null, null, null));
        }
        model.addAttribute("contratos", contratoService.listarContratos());
        return "faturamentos/form";
    }

    // Salvar faturamento
    @PostMapping
    public String salvarFaturamento(@ModelAttribute @Valid PostFaturamento faturamento, Model model) {
        try {
            faturamentoService.criarFaturamento(faturamento);
            return "redirect:/faturamentos";
        } catch (BadRequestException | NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("faturamento", faturamento);
            model.addAttribute("contratos", contratoService.listarContratos());
            return "faturamentos/form";
        }
    }

    // Editar faturamento
    @GetMapping("/editar/{id}")
    public String editarFaturamentoForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Faturamento faturamento = faturamentoService.buscarFaturamentoPorId(id);
            model.addAttribute("faturamento", faturamento);
            return "faturamentos/editar";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/faturamentos";
        }
    }

    // Atualizar faturamento
    @PostMapping("/editar/{id}")
    public String atualizarFaturamento(@PathVariable Long id, @ModelAttribute @Valid PutFaturamento faturamento,
            Model model) {
        try {
            faturamentoService.atualizarFaturamento(id, faturamento);
            return "redirect:/faturamentos";
        } catch (NotFoundException | BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("faturamento", faturamento);
            return "faturamentos/editar";
        }
    }

    // Excluir faturamento
    @GetMapping("/excluir/{id}")
    public String excluirFaturamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            faturamentoService.excluirFaturamento(id);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/faturamentos";
    }
}