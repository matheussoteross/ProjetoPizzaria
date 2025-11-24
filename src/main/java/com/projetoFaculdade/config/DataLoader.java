package com.projetoFaculdade.config;

import com.projetoFaculdade.entity.Papel;
import com.projetoFaculdade.entity.Usuario;
import com.projetoFaculdade.repository.PapelRepository;
import com.projetoFaculdade.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(
            PapelRepository papelRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            
            System.out.println("CRIANDO USUÁRIOS DE TESTE SEM VERIFICAÇÃO...");

            // 1. Cria e salva os Papéis (Roles)
            Papel papelAdmin = new Papel("ROLE_ADMIN"); 
            papelRepository.save(papelAdmin);
            Papel papelUser = new Papel("ROLE_USER");
            papelRepository.save(papelUser);
            
            // 2. Cria o Usuário Administrador
            Usuario admin = new Usuario();
            admin.setUsername("admin@pizzaria.com");
            admin.setPassword(passwordEncoder.encode("123456")); 
            admin.setAtivo(true);
            
            Set<Papel> papeisAdmin = new HashSet<>();
            papeisAdmin.add(papelAdmin);
            papeisAdmin.add(papelUser); // Admin tem papel de Admin e User
            admin.setPapeis(papeisAdmin);
            usuarioRepository.save(admin);

            // 3. Cria o Usuário Comum
            Usuario user = new Usuario();
            user.setUsername("user@pizzaria.com"); 
            user.setPassword(passwordEncoder.encode("123456")); 
            user.setAtivo(true);
            
            Set<Papel> papeisUser = new HashSet<>();
            papeisUser.add(papelUser);
            user.setPapeis(papeisUser);
            usuarioRepository.save(user);

            System.out.println("SEEDS INSERIDOS: admin@pizzaria.com e user@pizzaria.com (senha: 123456)");
        };
    }
}