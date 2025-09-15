package com.unifil.advocacia.gerenciador.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unifil.advocacia.gerenciador.cliente.dto.PostCliente;
import com.unifil.advocacia.gerenciador.cliente.dto.PutCliente;
import com.unifil.advocacia.gerenciador.cliente.model.Cliente;
import com.unifil.advocacia.gerenciador.cliente.repository.ClienteRepository;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(PostCliente dto) {
        if (!validarCpf(dto.cpf())) {
            throw new BadRequestException("CPF inválido");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setCpf(dto.cpf());
        cliente.setEndereco(dto.endereco());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, PutCliente dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            cliente.setNome(dto.nome());
        }
        if (dto.dataNascimento() != null) {
            cliente.setDataNascimento(dto.dataNascimento());
        }
        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            if (!validarCpf(dto.cpf())) {
                throw new BadRequestException("CPF inválido");
            }
            cliente.setCpf(dto.cpf());
        }
        if (dto.endereco() != null && !dto.endereco().isBlank()) {
            cliente.setEndereco(dto.endereco());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            cliente.setEmail(dto.email());
        }
        if (dto.telefone() != null && !dto.telefone().isBlank()) {
            cliente.setTelefone(dto.telefone());
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    public void excluirCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public boolean validarCpf(String cpf) {
        if (cpf == null) return false;

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // todos os dígitos iguais
        }

        try {
            int[] digits = cpf.chars().map(c -> c - '0').toArray();

            // Valida 1º dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += digits[i] * (10 - i);
            int firstCheck = (sum * 10) % 11;
            if (firstCheck == 10) firstCheck = 0;
            if (digits[9] != firstCheck) return false;

            // Valida 2º dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) sum += digits[i] * (11 - i);
            int secondCheck = (sum * 10) % 11;
            if (secondCheck == 10) secondCheck = 0;
            return digits[10] == secondCheck;

        } catch (Exception e) {
            return false;
        }
    }
}
