package com.projetoFaculdade.repository;

import com.projetoFaculdade.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, Long> {
    
    Papel findByNome(String nome);
}