package com.projetoFaculdade.service;

import com.projetoFaculdade.entity.Usuario;
import com.projetoFaculdade.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Injeção de Dependência
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null || !usuario.getAtivo()) { // Verificação corrigida
            throw new UsernameNotFoundException("Usuário não encontrado ou inativo: " + username);
        }

        // Mapeia os Papéis do seu Entity para o formato Authority do Spring Security
        String[] roles = usuario.getPapeis().stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList())
                .toArray(String[]::new); // Array::new é sintaxe mais moderna que new String[0]

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(roles) 
                .disabled(!usuario.getAtivo()) // O método 'disabled' usa o valor invertido do seu 'ativo'
                .build();
    }
}