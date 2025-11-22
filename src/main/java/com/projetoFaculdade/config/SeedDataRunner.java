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
public class SeedDataRunner {

    // Define este método como um Bean que será executado na inicialização
    @Bean
    public CommandLineRunner initDatabase(
            PapelRepository papelRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // Verifica se o banco já tem dados para evitar duplicidade
            if (usuarioRepository.count() == 0) {
                
                System.out.println("Criando dados iniciais (SEEDS)...");

                // 1. Cria e salva os Papéis
                Papel papelAdmin = new Papel("ROLE_ADMIN");
                Papel papelUser = new Papel("ROLE_USER");
                papelRepository.save(papelAdmin);
                papelRepository.save(papelUser);

                // 2. Cria o Usuário Administrador
                Usuario admin = new Usuario();
                admin.setUsername("admin@pizzaria.com"); // Credencial de teste
                // Codifica a senha "123456" usando o BCrypt
                admin.setPassword(passwordEncoder.encode("123456")); 
                admin.setAtivo(true);
                
                Set<Papel> papeisAdmin = new HashSet<>();
                papeisAdmin.add(papelAdmin);
                papeisAdmin.add(papelUser); // Admin também tem as permissões de User
                admin.setPapeis(papeisAdmin);
                usuarioRepository.save(admin);

                // 3. Cria o Usuário Comum
                Usuario user = new Usuario();
                user.setUsername("user@pizzaria.com"); // Credencial de teste
                // Codifica a senha "123456"
                user.setPassword(passwordEncoder.encode("123456")); 
                user.setAtivo(true);
                
                Set<Papel> papeisUser = new HashSet<>();
                papeisUser.add(papelUser);
                user.setPapeis(papeisUser);
                usuarioRepository.save(user);

                System.out.println("SEEDS criados: admin@pizzaria.com e user@pizzaria.com (senha: 123456)");
            }
        };
    }
}