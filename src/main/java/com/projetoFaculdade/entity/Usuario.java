package com.projetoFaculdade.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    private Boolean ativo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_papeis",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "papel_id")
    )
    private Set<Papel> papeis;

    // Construtor
    public Usuario() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() { // ESSENCIAL para UsuarioService
        return username;
    }

    public String getPassword() { // ESSENCIAL para UsuarioService
        return password;
    }

    public Boolean getAtivo() { // ESSENCIAL para UsuarioService
        return ativo;
    }

    public Set<Papel> getPapeis() { // ESSENCIAL para UsuarioService
        return papeis;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }
}