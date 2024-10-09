
# API de Gerenciamento de Pedidos e Móveis

Este projeto é uma API backend desenvolvida com **Spring Boot 2.7.0** e **Java 11**, que oferece funcionalidades para autenticação de usuários utilizando **JWT** e integração com **PostgreSQL** para a criação e atualização automática de tabelas no banco de dados. A API é documentada com **Swagger UI**, permitindo fácil exploração e teste dos endpoints.

## 🛠️ Tecnologias Utilizadas

- **Java 11**
- **Spring Boot 2.7.0**
- **JWT** para autenticação
- **PostgreSQL** como banco de dados
- **Swagger UI** para documentação
- **Maven** para gerenciamento de dependências

## 📦 Funcionalidades

- **Autenticação via JWT**: A API implementa login e registro de usuários com geração de tokens JWT para autenticação de rotas protegidas.
- **CRUD de Pedidos**: Permite criar, visualizar, aprovar, rejeitar e listar pedidos.
- **CRUD de Móveis**: Gerencia o cadastro de móveis, incluindo materiais e quantidades.
- **CRUD de Materiais**: Gerencia o cadastro de materiais utilizados na fabricação dos móveis.

## 🔧 Pré-requisitos

- **JDK 11** ou superior
- **PostgreSQL** instalado e rodando localmente (ou via Docker)
- **Maven** instalado

## 🚀 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/brenoloa/madeiradeleifullstack.git
   ```
   
2. Entre na pasta do projeto:
   ```bash
   cd fabricamoveis
   ```

3. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Execute o projeto com Maven:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse a documentação do **Swagger UI**:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## 📄 Documentação

A documentação completa dos endpoints da API pode ser acessada via Swagger UI após a execução do projeto. Isso inclui exemplos de requests, parâmetros e respostas.
A Documentação também se encontra em PDF no repo.

Exemplos de endpoints documentados:

- **Autenticação**:
  - `POST /api/auth/signup`: Cadastro de novos usuários.
  - `POST /api/auth/login`: Login com geração de token JWT.
  
- **Pedidos**:
  - `POST /api/pedidos`: Criar novo pedido.
  - `GET /api/pedidos`: Listar todos os pedidos.
  - `PUT /api/pedidos/{id}/aprovar`: Aprovar um pedido.
  - `PUT /api/pedidos/{id}/rejeitar`: Rejeitar um pedido.

- **Móveis**:
  - `GET /api/moveis`: Listar todos os móveis.
  - `POST /api/moveis`: Criar um novo móvel.
  - `PUT /api/moveis/{id}`: Atualizar dados de um móvel.
  - `DELETE /api/moveis/{id}`: Excluir um móvel.


## 📌 Requisitos Adicionais

- **PostgreSQL**: Certifique-se de que o banco de dados está rodando e configurado corretamente.
- **JWT**: As rotas protegidas necessitam de um token JWT válido, obtido após o login.


