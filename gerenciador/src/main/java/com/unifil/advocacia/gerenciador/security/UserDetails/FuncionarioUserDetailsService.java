package com.unifil.advocacia.gerenciador.security.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;
import com.unifil.advocacia.gerenciador.funcionario.repository.FuncionarioRepository;

@Service
public class FuncionarioUserDetailsService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new FuncionarioUserDetails(funcionario); 
    }
}
