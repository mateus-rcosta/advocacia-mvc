package com.unifil.advocacia.gerenciador.funcionario.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.unifil.advocacia.gerenciador.funcionario.dto.PostFuncionario;
import com.unifil.advocacia.gerenciador.funcionario.dto.PutFuncionario;
import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;
import com.unifil.advocacia.gerenciador.funcionario.service.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Listar funcionários
    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioService.listarFuncionarios());
        return "funcionarios/listar"; 
    }

    // Exibir formulário de criação
    @GetMapping("/novo")
    public String novoFuncionarioForm(Model model) {
        model.addAttribute("funcionario", new PostFuncionario("", "", "", null));
        return "funcionarios/form";
    }

    // Criar funcionário
    @PostMapping
    public String salvarFuncionario(@ModelAttribute PostFuncionario funcionario) {
        funcionarioService.salvarFuncionario(funcionario);
        return "redirect:/funcionarios";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarFuncionarioForm(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
        model.addAttribute("funcionario", funcionario);
        return "funcionarios/editar";
    }

    // Atualizar funcionário
    @PostMapping("/editar/{id}")
    public String atualizarFuncionario(@PathVariable Long id, @ModelAttribute PutFuncionario funcionario) {
        funcionarioService.atualizarFuncionario(id, funcionario);
        return "redirect:/funcionarios";
    }

    // Excluir funcionário
    @GetMapping("/excluir/{id}")
    public String excluirFuncionario(@PathVariable Long id) {
        funcionarioService.excluirFuncionario(id);
        return "redirect:/funcionarios";
    }
}