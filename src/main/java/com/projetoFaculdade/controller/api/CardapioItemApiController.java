package com.projetoFaculdade.controller.api;

import com.projetoFaculdade.entity.CardapioItem;
import com.projetoFaculdade.service.CardapioItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; // Importante para controle de acesso

import java.util.List;

@RestController // Indica que esta é uma API REST que retorna JSON
@RequestMapping("/api/itens") // Define a rota base da API
public class CardapioItemApiController {

    private final CardapioItemService cardapioItemService;

    // Injeção de dependência do Service
    public CardapioItemApiController(CardapioItemService cardapioItemService) {
        this.cardapioItemService = cardapioItemService;
    }

    // 1. GET: Listar todos os itens (Rota pública)
    // GET /api/itens
    @GetMapping
    public ResponseEntity<List<CardapioItem>> listarTodos() {
        List<CardapioItem> itens = cardapioItemService.listarTodos();
        return ResponseEntity.ok(itens); // Retorna status 200 OK com a lista de itens
    }

    // 2. GET: Buscar item por ID (Rota pública)
    // GET /api/itens/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CardapioItem> buscarPorId(@PathVariable Long id) {
        return cardapioItemService.buscarPorId(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK
                .orElse(ResponseEntity.notFound().build()); // Se não encontrar, retorna 404 Not Found
    }

    // 3. POST: Criar um novo item (Exige autenticação)
    // POST /api/itens
    // @PreAuthorize("isAuthenticated()") garante que apenas usuários logados podem usar este endpoint
    @PostMapping
    @PreAuthorize("isAuthenticated()") 
    public ResponseEntity<CardapioItem> criarItem(@Valid @RequestBody CardapioItem item) {
        CardapioItem novoItem = cardapioItemService.salvar(item);
        // Retorna 201 Created e o item recém-criado
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    // 4. PUT: Atualizar um item existente (Exige autenticação)
    // PUT /api/itens/{id}
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CardapioItem> atualizarItem(@PathVariable Long id, @Valid @RequestBody CardapioItem itemDetalhes) {
        return cardapioItemService.buscarPorId(id)
                .map(itemExistente -> {
                    // Atualiza os dados do item existente
                    itemExistente.setNome(itemDetalhes.getNome());
                    itemExistente.setDescricao(itemDetalhes.getDescricao());
                    itemExistente.setPreco(itemDetalhes.getPreco());
                    itemExistente.setCategoria(itemDetalhes.getCategoria());
                    
                    // Salva a atualização no banco
                    CardapioItem itemAtualizado = cardapioItemService.salvar(itemExistente);
                    return ResponseEntity.ok(itemAtualizado);
                })
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se o item não existir
    }

    // 5. DELETE: Remover um item (Exige autenticação)
    // DELETE /api/itens/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        if (cardapioItemService.buscarPorId(id).isPresent()) {
            cardapioItemService.remover(id);
            // Retorna 204 No Content para indicar sucesso na remoção
            return ResponseEntity.noContent().build();
        } else {
            // Retorna 404 se o item não for encontrado para remoção
            return ResponseEntity.notFound().build();
        }
    }
}