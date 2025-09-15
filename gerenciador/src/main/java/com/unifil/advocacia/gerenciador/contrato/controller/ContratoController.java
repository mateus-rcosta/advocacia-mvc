package com.unifil.advocacia.gerenciador.contrato.controller;

import com.unifil.advocacia.gerenciador.cliente.service.ClienteService;
import com.unifil.advocacia.gerenciador.contrato.dto.PostContrato;
import com.unifil.advocacia.gerenciador.contrato.dto.PutContrato;
import com.unifil.advocacia.gerenciador.contrato.model.Contrato;

import com.unifil.advocacia.gerenciador.contrato.service.ContratoService;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.funcionario.service.FuncionarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contratos")
@Validated
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarContratos(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        model.addAttribute("contratos", contratoService.listarContratos());
        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "contratos/listar";
    }

    @GetMapping("/novo")
    public String novoContratoForm(Model model) {
        if (!model.containsAttribute("contrato")) {
            model.addAttribute("contrato", new PostContrato("", "", null, null, null, ""));
        }
        model.addAttribute("cliente", clienteService.listarClientes()); 
        return "contratos/form";
    }

    @PostMapping
    public String salvarContrato(@ModelAttribute @Valid PostContrato contrato, Model model) {
        try {
            contratoService.criarContrato(contrato);
            return "redirect:/contratos";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("contrato", contrato);
            return "contratos/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarContratoForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Contrato contrato = contratoService.buscarContratoPorId(id);
            model.addAttribute("contrato", contrato);
            model.addAttribute("funcionarios", funcionarioService.listarFuncionarios());
            return "contratos/editar";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/contratos";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizarContrato(@PathVariable Long id, @ModelAttribute @Valid PutContrato contrato, Model model) {
        try {
            contratoService.atualizarContrato(id, contrato);
            return "redirect:/contratos";
        } catch (NotFoundException | BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("contrato", contrato);
            return "contratos/editar";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirContrato(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            contratoService.excluirContrato(id);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/contratos";
    }
}
