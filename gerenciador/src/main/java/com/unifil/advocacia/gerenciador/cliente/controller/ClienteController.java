package com.unifil.advocacia.gerenciador.cliente.controller;

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

import com.unifil.advocacia.gerenciador.cliente.dto.PostCliente;
import com.unifil.advocacia.gerenciador.cliente.dto.PutCliente;
import com.unifil.advocacia.gerenciador.cliente.model.Cliente;
import com.unifil.advocacia.gerenciador.cliente.service.ClienteService;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar clientes
    @GetMapping
    public String listarClientes(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "clientes/listar";
    }

    // Formulário de novo cliente
    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new PostCliente("", null, "", "", "", ""));
        }
        return "clientes/form";
    }

    // Criar cliente
    @PostMapping
    public String salvarCliente(@ModelAttribute @Valid PostCliente cliente, Model model) {
        try {
            clienteService.criarCliente(cliente);
            return "redirect:/clientes";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/form";
        }
    }

    // Formulário de edição
    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            model.addAttribute("cliente", cliente);
            return "clientes/editar";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/clientes";
        }
    }

    // Atualizar cliente
    @PostMapping("/editar/{id}")
    public String atualizarCliente(@PathVariable Long id, @ModelAttribute @Valid PutCliente cliente, Model model) {
        try {
            clienteService.atualizarCliente(id, cliente);
            return "redirect:/clientes";
        } catch (BadRequestException | NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/editar";
        }
    }

    // Excluir cliente
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.excluirCliente(id);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/clientes";
    }
}