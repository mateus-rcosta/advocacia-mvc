package com.unifil.advocacia.gerenciador.funcionario.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.funcionario.dto.PostFuncionario;
import com.unifil.advocacia.gerenciador.funcionario.dto.PutFuncionario;
import com.unifil.advocacia.gerenciador.funcionario.enums.Funcao;
import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;
import com.unifil.advocacia.gerenciador.funcionario.repository.FuncionarioRepository;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (funcionarioRepository.count() == 0) {
            salvarFuncionario(new PostFuncionario("admin", "admin@admin.com", "admin", Funcao.ADMINISTRADOR));
        }
    }

    @Transactional
    public Funcionario salvarFuncionario(PostFuncionario dto) {
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("Já existe um funcionário com esse e-mail.");
        }
        Funcionario funcionario = new Funcionario(
                dto.nome(),
                dto.email(),
                passwordEncoder.encode(dto.senha()),
                dto.funcao() == null ? Funcao.USUARIO : dto.funcao()
        );
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado com o ID informado"));
    }

    @Transactional
    public Funcionario atualizarFuncionario(Long id, PutFuncionario dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            funcionario.setNome(dto.nome());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            funcionario.setEmail(dto.email());
        }
        if (dto.senha() != null && !dto.senha().isBlank()) {
            funcionario.setSenha(passwordEncoder.encode(dto.senha()));
        }
        if (dto.funcao() != null) {
            funcionario.setFuncao(dto.funcao());
        }

        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void excluirFuncionario(Long id) {
        existsById(id);
        funcionarioRepository.deleteById(id);
    }

    private void existsById(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new NotFoundException("Funcionário não encontrado com o ID informado");
        }
    }
}