Aplicação Web com Java/Spring + Thymeleaf — Projeto Pizzaria

1. Sobre o Projeto

Nome do projeto: Projeto Pizzaria (projetoFaculdade)

Resumo (5–8 linhas):
Este é um sistema de gerenciamento de cardápio e catálogo de pedidos online para uma pizzaria. O sistema utiliza a arquitetura em camadas (Controller, Service, Repository) com segurança implementada via Spring Security, perfis de acesso e senhas criptografadas (BCrypt). O frontend é renderizado pelo Thymeleaf e a aplicação oferece um CRUD completo para a entidade principal CardapioItem, além de uma API REST para integração externa.

Escopo mínimo: CRUD completo da entidade CardapioItem, login com perfis ADMIN e USER, área restrita e API REST.

2. Tecnologias

Backend: Java 17+, Spring Boot 3+, Spring Web, Spring Data JPA, Spring Security, Validation.

Frontend: Thymeleaf, HTML/CSS/JavaScript (Frontend Renderizado no Servidor).

Banco de Dados: MySQL 8+.

Build Tool: Apache Maven.

3. Arquitetura (Resumo)

Camadas: O projeto segue o padrão de três camadas: Controller (Web/API) → Service (Lógica de Negócio) → Repository (Acesso a Dados).

Segurança: Autenticação por formulário em /login com perfis ROLE_ADMIN e ROLE_USER. Senhas armazenadas com BCrypt.

Persistência: MySQL com mapeamento Objeto-Relacional (ORM) via Spring Data JPA/Hibernate.

4. Requisitos de Ambiente

JDK 17+

Apache Maven 3.8+

MySQL 8+

5. Configuração do Banco

Crie um banco de dados vazio no seu servidor MySQL.

Exemplo SQL:

CREATE DATABASE projeto_pizzaria_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


6. Configuração da Aplicação

Edite o arquivo src/main/resources/application.properties com suas credenciais de acesso ao MySQL:

spring.datasource.url=jdbc:mysql://localhost:3306/projeto_pizzaria_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=SEU_USUARIO_DO_MYSQL
spring.datasource.password=SUA_SENHA_DO_MYSQL

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false


7. Instalação e Execução

Clone o repositório:

git clone [https://github.com/SEU_USUARIO/SEU_REPO.git](https://github.com/SEU_USUARIO/SEU_REPO.git)
cd SEU_REPO


Crie o banco de dados (conforme Seção 5).

Execute o projeto (o Spring Boot irá criar as tabelas e inserir os Seeds de Usuários automaticamente):

mvn spring-boot:run


Acesse: http://localhost:8080

8. Seeds de Usuários (Login de Teste)

Os usuários de teste são criados automaticamente pelo SeedDataRunner.java na primeira inicialização:

Usuário

Senha

Perfis

admin@pizzaria.com

123456

ROLE_ADMIN, ROLE_USER

user@pizzaria.com

123456

ROLE_USER

9. Segurança (Rotas e Acesso)

Públicas: /, /login, /css/**, /js/**, /images/**

Autenticadas (USER ou ADMIN): /app/** (Ex: /app/cardapio)

Apenas ADMIN: /admin/** (Painel de gerenciamento de itens)

Login: Por formulário em /login. Logout padrão em /logout.

10. Rotas Web (Thymeleaf)

/ (Página inicial, pública)

/app/dashboard (Página de boas-vindas para usuários logados)

/admin/itens (Listar e Gerenciar itens do cardápio — ADMIN)

11. API REST (Base: /api/itens)

A API REST manipula a entidade CardapioItem e exige autenticação para operações de escrita (POST, PUT, DELETE).

Método

Rota

Descrição

Acesso

GET

/api/itens

Lista todos os itens do cardápio

Público

GET

/api/itens/{id}

Detalhe de um item

Público

POST

/api/itens

Cria um novo item (Pizza, Bebida)

Autenticado

PUT

/api/itens/{id}

Atualiza um item existente

Autenticado

DELETE

/api/itens/{id}

Remove um item

Autenticado

Exemplo de criação (curl):

curl -X POST http://localhost:8080/api/itens \
-H "Content-Type: application/json" \
--user "admin@pizzaria.com:123456" \
-d '{"nome":"Pizza Marguerita", "descricao":"Clássica com manjericão", "preco":45.00, "categoria":"Pizza Salgada"}'


12. Links de Entrega

Repositório GitHub: https://github.com/matheussoteross/ProjetoPizzaria
13. Autores
Matheus Sotero Tenorio dos Santos	3025102324
Cauã Olivare Leandro	3025101217
[SEU NOME COMPLETO] — RA [SEU RA]

[NOME DO SEU PARCEIRO (se houver)] — RA [RA DO PARCEIRO]
