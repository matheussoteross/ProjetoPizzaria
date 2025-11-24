package com.projetoFaculdade.service;

import com.projetoFaculdade.entity.Papel;
import com.projetoFaculdade.entity.Usuario;
import com.projetoFaculdade.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Importação necessária
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // A injeção do PasswordEncoder não é mais necessária aqui, pois o Spring Security
    // já lida com o PasswordEncoder via DaoAuthenticationProvider (SecurityConfig).
    
    // Injeção de Dependência do Repository
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null || !usuario.getAtivo()) {
            throw new UsernameNotFoundException("Usuário não encontrado ou inativo: " + username);
        }

        // Converte o Set<Papel> do nosso modelo para Collection<GrantedAuthority> do Spring Security
        Collection<? extends GrantedAuthority> authorities = usuario.getPapeis().stream()
                .map(papel -> new SimpleGrantedAuthority(papel.getNome()))
                .collect(Collectors.toSet());

        // Retorna um objeto UserDetails que o Spring Security entende
        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(),
            usuario.getPassword(), // Hash BCrypt
            authorities
        );
    }
}