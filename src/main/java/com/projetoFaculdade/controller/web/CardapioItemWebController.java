package com.projetoFaculdade.controller.web;

import com.projetoFaculdade.entity.CardapioItem;
import com.projetoFaculdade.service.CardapioItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/app", "/admin"}) // Mapeia rotas base para /app e /admin
public class CardapioItemWebController {

    private final CardapioItemService cardapioItemService;

    // Injeção de dependência do Service
    public CardapioItemWebController(CardapioItemService cardapioItemService) {
        this.cardapioItemService = cardapioItemService;
    }

    // Rota de Dashboard (Área Restrita - Acesso: USER ou ADMIN)
    // GET /app/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("username", "Usuário Logado"); // Apenas um placeholder
        return "dashboard"; // Retorna o template dashboard.html
    }

    // Rota de Listagem do CRUD (Acesso: APENAS ADMIN)
    // GET /admin/itens
    @GetMapping("/itens")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarItens(Model model) {
        List<CardapioItem> itens = cardapioItemService.listarTodos();
        model.addAttribute("itens", itens);
        return "admin/lista-itens"; // Retorna o template lista-itens.html
    }

    // Rota para o Formulário de Criação (Acesso: APENAS ADMIN)
    // GET /admin/itens/novo
    @GetMapping("/itens/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularioNovoItem(Model model) {
        model.addAttribute("item", new CardapioItem()); // Objeto vazio para o formulário
        model.addAttribute("action", "/admin/itens/salvar"); // Ação do formulário
        return "admin/form-item"; // Retorna o template form-item.html
    }

    // Rota para o Formulário de Edição (Acesso: APENAS ADMIN)
    // GET /admin/itens/editar/{id}
    @GetMapping("/itens/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularioEditarItem(@PathVariable Long id, Model model) {
        Optional<CardapioItem> itemOpt = cardapioItemService.buscarPorId(id);
        
        if (itemOpt.isPresent()) {
            model.addAttribute("item", itemOpt.get());
            model.addAttribute("action", "/admin/itens/salvar");
            return "admin/form-item";
        }
        // Se não encontrar, redireciona para a lista
        return "redirect:/admin/itens";
    }

    // Rota para SALVAR (Criação ou Edição) (Acesso: APENAS ADMIN)
    // POST /admin/itens/salvar
    @PostMapping("/itens/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarItem(@ModelAttribute("item") CardapioItem item) {
        // Validação básica de preço (exemplo de lógica de negócio)
        if (item.getPreco() == null || item.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            // Em um projeto real, você retornaria o formulário com a mensagem de erro.
            System.err.println("Erro: Preço inválido!");
            return "redirect:/admin/itens/novo"; 
        }
        cardapioItemService.salvar(item);
        return "redirect:/admin/itens";
    }

    // Rota para DELETAR (Acesso: APENAS ADMIN)
    // GET /admin/itens/remover/{id}
    @GetMapping("/itens/remover/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String removerItem(@PathVariable Long id) {
        cardapioItemService.remover(id);
        return "redirect:/admin/itens";
    }
}