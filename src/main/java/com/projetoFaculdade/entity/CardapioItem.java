package com.projetoFaculdade.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

// CORREÇÃO: Define um nome de entidade explícito para evitar conflito com o Repository
@Entity(name = "Item") 
@Table(name = "itens_cardapio")
public class CardapioItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco; 

    @Column(nullable = false, length = 50)
    private String categoria; 

    // Construtor padrão (obrigatório pelo JPA)
    public CardapioItem() {}

    // Construtor útil (opcional, mas bom para testes)
    public CardapioItem(String nome, String descricao, BigDecimal preco, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    // --- GETTERS ---
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    // --- SETTERS ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}