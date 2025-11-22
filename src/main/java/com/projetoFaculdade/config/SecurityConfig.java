package com.projetoFaculdade.config;

import com.projetoFaculdade.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    // Construtor que recebe as dependências (UsuarioService e PasswordEncoder)
    public SecurityConfig(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Define o codificador de senhas (BCrypt) - Mantido para a injeção
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Configura o provedor de autenticação (CORRIGIDO PARA O SEU REQUISITO)
    // O Maven exige que o UserDetailsService seja passado no construtor,
    // E o PasswordEncoder seja configurado pelo setter.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // CORREÇÃO CRÍTICA: Passamos o UserDetailsService (usuarioService) no construtor, 
        // conforme o Maven indicou que era "required".
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(usuarioService); 
        
        // Configuramos o PasswordEncoder usando o setter.
        // O método 'setUserDetailsService' não é necessário porque já passamos no construtor.
        authProvider.setPasswordEncoder(passwordEncoder); 
        
        return authProvider;
    }

    // 3. Configura o Filtro de Segurança (Regras de Acesso e Rotas)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() 
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/app/**").authenticated() 
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