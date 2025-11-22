package com.projetoFaculdade.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Rota da Página Inicial (PÚBLICA)
    // GET /
    @GetMapping("/")
    public String index() {
        return "index"; // Retorna o template index.html
    }
    
    // Rota de Login (PÚBLICA)
    // GET /login
    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna o template login.html
    }
}