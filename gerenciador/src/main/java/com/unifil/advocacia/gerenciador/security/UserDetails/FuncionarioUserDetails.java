package com.unifil.advocacia.gerenciador.security.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.unifil.advocacia.gerenciador.funcionario.model.Funcionario;

import java.util.Collection;
import java.util.List;

public class FuncionarioUserDetails implements UserDetails {

    private final Funcionario funcionario;

    public FuncionarioUserDetails(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + funcionario.getFuncao().name()));
    }

    @Override
    public String getPassword() {
        return funcionario.getSenha();
    }

    @Override
    public String getUsername() {
        return funcionario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}