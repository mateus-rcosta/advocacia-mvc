package com.unifil.advocacia.gerenciador.contrato.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unifil.advocacia.gerenciador.contrato.dto.PostContrato;
import com.unifil.advocacia.gerenciador.contrato.dto.PutContrato;
import com.unifil.advocacia.gerenciador.contrato.model.Contrato;
import com.unifil.advocacia.gerenciador.contrato.repository.ContratoRepository;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;
import com.unifil.advocacia.gerenciador.funcionario.repository.FuncionarioRepository;
import com.unifil.advocacia.gerenciador.security.UserDetails.FuncionarioUserDetails;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Contrato criarContrato(PostContrato dto) {
        Contrato contrato = new Contrato();
        contrato.setTitulo(dto.titulo());
        contrato.setDescricao(dto.descricao());
        contrato.setDataInicio(dto.dataInicio());
        contrato.setDataFim(dto.dataFim());
        contrato.setTipo(dto.tipo());

        // Associa o funcionário logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FuncionarioUserDetails userDetails = (FuncionarioUserDetails) auth.getPrincipal();
        contrato.setFuncionario(userDetails.getFuncionario());

        return contratoRepository.save(contrato);
    }

    public List<Contrato> listarContratos() {
        return contratoRepository.findAll();
    }

    public Contrato buscarContratoPorId(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato não encontrado com o ID informado"));
    }

    public Contrato atualizarContrato(Long id, PutContrato dto) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato não encontrado"));

        if (dto.titulo() != null && !dto.titulo().isBlank()) {
            contrato.setTitulo(dto.titulo());
        }
        if (dto.descricao() != null && !dto.descricao().isBlank()) {
            contrato.setDescricao(dto.descricao());
        }
        if (dto.dataInicio() != null) {
            contrato.setDataInicio(dto.dataInicio());
        }
        if (dto.dataFim() != null) {
            contrato.setDataFim(dto.dataFim());
        }
        if (dto.tipo() != null && !dto.tipo().isBlank()) {
            contrato.setTipo(dto.tipo());
        }
        if (dto.status() != null) {
            contrato.setStatus(dto.status());
        }
        if (dto.funcionarioId() != null) {
            Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
                    .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
            contrato.setFuncionario(funcionario);
        }

        return contratoRepository.save(contrato);
    }

    public void excluirContrato(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new NotFoundException("Contrato não encontrado com o ID informado");
        }
        contratoRepository.deleteById(id);
    }
}
