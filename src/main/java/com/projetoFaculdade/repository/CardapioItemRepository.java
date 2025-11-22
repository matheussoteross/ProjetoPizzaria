package com.projetoFaculdade.repository;

import com.projetoFaculdade.entity.CardapioItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioItemRepository extends JpaRepository<CardapioItem, Long> {
    // O JpaRepository fornece os métodos CRUD: save, findAll, findById, deleteById.
}