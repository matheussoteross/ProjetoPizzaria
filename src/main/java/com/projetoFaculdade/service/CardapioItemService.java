package com.projetoFaculdade.service;

import com.projetoFaculdade.entity.CardapioItem;
import com.projetoFaculdade.repository.CardapioItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardapioItemService {

    private final CardapioItemRepository cardapioItemRepository;

    // Injeção de dependência do Repository via construtor
    public CardapioItemService(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    // Método para listar todos os itens do cardápio
    public List<CardapioItem> listarTodos() {
        return cardapioItemRepository.findAll();
    }

    // Método para buscar um item pelo ID
    public Optional<CardapioItem> buscarPorId(Long id) {
        return cardapioItemRepository.findById(id);
    }

    // Método para salvar ou atualizar um item (CRUD - Create/Update)
    // O @Transactional garante que a operação no DB seja atômica
    @Transactional
    public CardapioItem salvar(CardapioItem item) {
        // ** FUTURA VALIDAÇÃO **
        // Aqui é onde você adicionaria a lógica de negócio, como:
        // 1. Verificar se o nome do item já existe: 
        //    if (repository.existsByNome(item.getNome()) && item.getId() == null) { throw new RuntimeException("Item já existe"); }
        // 2. Garantir que o preço não é negativo.
        
        return cardapioItemRepository.save(item);
    }

    // Método para remover um item pelo ID (CRUD - Delete)
    @Transactional
    public void remover(Long id) {
        // Poderíamos adicionar uma verificação se o item existe antes de deletar
        cardapioItemRepository.deleteById(id);
    }

    // Exemplo de uma regra de negócio específica (não CRUD)
    public List<CardapioItem> buscarPorCategoria(String categoria) {
        // Se a sua entidade/repositório tivesse este método, a lógica viria aqui.
        // Por exemplo: return cardapioItemRepository.findByCategoria(categoria);
        return null; // Apenas um placeholder
    }
}