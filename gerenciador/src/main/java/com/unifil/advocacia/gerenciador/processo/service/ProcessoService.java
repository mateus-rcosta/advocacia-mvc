package com.unifil.advocacia.gerenciador.processo.service;

import com.unifil.advocacia.gerenciador.contrato.model.Contrato;
import com.unifil.advocacia.gerenciador.contrato.repository.ContratoRepository;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.processo.dto.PostProcesso;
import com.unifil.advocacia.gerenciador.processo.dto.PutProcesso;
import com.unifil.advocacia.gerenciador.processo.enums.StatusProcesso;
import com.unifil.advocacia.gerenciador.processo.model.Processo;
import com.unifil.advocacia.gerenciador.processo.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    public Processo criarProcesso(PostProcesso dto) {
        Processo processo = new Processo();
        processo.setNumero(dto.numero());
        processo.setTipo(dto.tipo().name());
        processo.setDescricao(dto.descricao());
        processo.setDataAbertura(dto.dataAbertura());
        processo.setStatus(StatusProcesso.EM_ANDAMENTO);

        Contrato contrato = contratoRepository.findById(dto.contratoId())
                .orElseThrow(() -> new NotFoundException("Contrato não encontrado"));
        processo.setContrato(contrato);

        return processoRepository.save(processo);
    }

    public List<Processo> listarProcessos() {
        return processoRepository.findAll();
    }

    public Processo buscarProcessoPorId(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado com o ID informado"));
    }

    public Processo atualizarProcesso(Long id, PutProcesso dto) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

        if (dto.numero() != null && !dto.numero().isBlank()) {
            processo.setNumero(dto.numero());
        }
        if (dto.descricao() != null && !dto.descricao().isBlank()) {
            processo.setDescricao(dto.descricao());
        }
        if (dto.tipo() != null) {
            processo.setTipo(dto.tipo().name());
        }
        if (dto.dataAbertura() != null) {
            processo.setDataAbertura(dto.dataAbertura());
        }
        if (dto.dataEncerramento() != null) {
            processo.setDataEncerramento(dto.dataEncerramento());
        }
        if (dto.statusProcesso() != null) {
            processo.setStatus(dto.statusProcesso());
        }

        return processoRepository.save(processo);
    }

    public void excluirProcesso(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new NotFoundException("Processo não encontrado com o ID informado");
        }
        processoRepository.deleteById(id);
    }
}