# API Restful para Gestão de Hábitos

Este projeto é uma API Restful desenvolvida em Java utilizando o framework Spring Boot. A API foi criada como parte de um projeto acadêmico para a disciplina de Gestão e Qualidade de Software. O objetivo principal é gerenciar usuários, hábitos e registros de hábitos.

## Tecnologias Utilizadas

- **Java 24**
- **Spring Boot**
- **Maven**
- **H2 Database**
- **Spring Security**
- **Jakarta Validation**
- **BCrypt**

## Estrutura do Projeto

A estrutura do projeto segue o padrão do Spring Boot, com os seguintes pacotes principais:

- **controller**: Contém os controladores responsáveis por expor os endpoints da API.
- **domain**: Contém as classes que representam as entidades do banco de dados e DTOs.
- **repository**: Contém as interfaces de repositório para acesso ao banco de dados.
- **infra**: Contém classes relacionadas à infraestrutura, como serviços de segurança.

## Funcionalidades Implementadas

### Usuários
- **Cadastro de Usuários**: Endpoint para criar novos usuários. A data de criação é automaticamente registrada.
- **Login de Usuários**: Endpoint para autenticar usuários e gerar tokens JWT.
- **Deleção de Usuários**: Endpoint para deletar usuários e seus hábitos associados.

### Hábitos
- **Cadastro de Hábitos**: Endpoint para criar novos hábitos para um usuário.
- **Listagem de Hábitos**: Endpoint para listar os hábitos de um usuário.
- **Deleção de Hábitos**: Endpoint para deletar hábitos de um usuário.

## Endpoints

### Autenticação
- `POST /auth/register`: Registra um novo usuário.
- `POST /auth/login`: Autentica um usuário e retorna um token JWT.
- `DELETE /auth/delete`: Deleta um usuário autenticado e seus hábitos.

### Hábitos
- `POST /habits`: Cria um novo hábito para o usuário autenticado.
- `GET /habits`: Lista os hábitos do usuário autenticado.
- `DELETE /habits/{id}`: Deleta um hábito específico do usuário autenticado.

## Configuração do Banco de Dados

O projeto utiliza o banco de dados em memória H2 para facilitar o desenvolvimento e os testes. As configurações estão no arquivo `application.properties`.

- URL do banco de dados: `jdbc:h2:mem:database`
- Console H2 habilitado: `/h2-console`
- Usuário: `admin`
- Senha: `admin`

## Dependências Principais

As dependências principais do projeto estão listadas no arquivo `pom.xml`:

- **Spring Boot Starter Web**: Para criação de APIs RESTful.
- **Spring Boot Starter Data JPA**: Para integração com o banco de dados.
- **Spring Boot Starter Security**: Para autenticação e autorização.
- **H2 Database**: Banco de dados em memória.
- **Lombok**: Para reduzir o boilerplate no código.
- **Spring Boot DevTools**: Para facilitar o desenvolvimento com hot reload.

## Como Baixar e Executar o Projeto

1. Certifique-se de ter o Java 21 ou superior instalado.
2. Clone este repositório:
   ```sh
   git clone <URL_DO_REPOSITORIO>