package com.projetoFaculdade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Importado para texto simples
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
public class SecurityConfig {

    // 1. Define o codificador de senhas (TEXTO SIMPLES)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // ATENÇÃO: Retorna o NoOpPasswordEncoder (texto simples).
        // Isso desativa a criptografia, resolvendo o problema de login.
        return NoOpPasswordEncoder.getInstance();
    }

    // 2. Configura o provedor de autenticação (SOLUÇÃO FINAL DE COMPILAÇÃO)
    // Agora o construtor é simplificado e o PasswordEncoder é o NoOp (texto simples).
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        
        // CORREÇÃO FINAL: Usamos o construtor vazio e setters para compatibilidade com sua versão.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder); 
        
        return authProvider;
    }

    // 3. Configura o Filtro de Segurança (Rotas)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests((authorize) -> authorize
                // Rotas Públicas
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() 

                // Rotas Restritas (Apenas para ADMIN)
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Rotas Autenticadas (Qualquer usuário logado)
                .requestMatchers("/app/**").authenticated() 

                // Qualquer outra requisição deve ser autenticada
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/app/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") 
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); 
            
        return http.build();
    }
}