package com.projetoFaculdade.repository;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity // 1. Mapeia para uma tabela no DB
@Table(name = "itens_cardapio")
public class CardapioItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    private String descricao;
    
    // Usar BigDecimal para precisão de moedas
    private BigDecimal preco; 

    // Relacionamento Opcional: Item pode pertencer a uma Categoria
    // @ManyToOne 
    // private Categoria categoria; 

    // Getters and Setters...
}