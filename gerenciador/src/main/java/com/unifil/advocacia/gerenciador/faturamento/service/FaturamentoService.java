package com.unifil.advocacia.gerenciador.faturamento.service;

import com.unifil.advocacia.gerenciador.contrato.model.Contrato;
import com.unifil.advocacia.gerenciador.contrato.repository.ContratoRepository;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.faturamento.dto.PostFaturamento;
import com.unifil.advocacia.gerenciador.faturamento.dto.PutFaturamento;
import com.unifil.advocacia.gerenciador.faturamento.enums.StatusFaturamento;
import com.unifil.advocacia.gerenciador.faturamento.model.Faturamento;
import com.unifil.advocacia.gerenciador.faturamento.repository.FaturamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaturamentoService {

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    public Faturamento criarFaturamento(PostFaturamento dto) {
        Contrato contrato = contratoRepository.findById(dto.contratoId())
                .orElseThrow(() -> new NotFoundException("Contrato não encontrado"));

        Faturamento faturamento = new Faturamento();
        faturamento.setValor(dto.valor());
        faturamento.setDataEmissao(dto.dataEmissao());
        faturamento.setContrato(contrato);
        faturamento.setStatus(StatusFaturamento.PENDENTE);
        return faturamentoRepository.save(faturamento);
    }

    public List<Faturamento> listarFaturamentos() {
        return faturamentoRepository.findAll();
    }

    public Faturamento buscarFaturamentoPorId(Long id) {
        return faturamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faturamento não encontrado com o ID informado"));
    }

    public Faturamento atualizarFaturamento(Long id, PutFaturamento dto) {
        Faturamento faturamento = faturamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faturamento não encontrado"));

        if (dto.valor() != null) {
            faturamento.setValor(dto.valor());
        }
        if (dto.dataPagamento() != null) {
            faturamento.setDataPagamento(dto.dataPagamento());
        }
        if (dto.status() != null) {
            faturamento.setStatus(dto.status());
        }

        return faturamentoRepository.save(faturamento);
    }

    public void excluirFaturamento(Long id) {
        if (!faturamentoRepository.existsById(id)) {
            throw new NotFoundException("Faturamento não encontrado com o ID informado");
        }
        faturamentoRepository.deleteById(id);
    }
}
