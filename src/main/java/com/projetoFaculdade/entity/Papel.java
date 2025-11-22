package com.projetoFaculdade.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "papeis")
public class Papel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome; 

    // Construtor
    public Papel() {}
    public Papel(String nome) {
        this.nome = nome;
    }
    
    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() { // ESSENCIAL para UsuarioService (p.getNome())
        return nome;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}