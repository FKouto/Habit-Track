# API Restful para Gestão de Hábitos

Este projeto é uma API Restful desenvolvida em Java utilizando o framework Spring Boot. A API foi criada como parte de um projeto acadêmico para a disciplina de Gestão e Qualidade de Software. O objetivo principal é gerenciar usuários, hábitos e registros de hábitos.

## Estrutura do Projeto

A estrutura do projeto segue o padrão do Spring Boot, com os seguintes pacotes principais:

- **controller**: Contém os controladores responsáveis por expor os endpoints da API.
- **model**: Contém as classes que representam as entidades do banco de dados.
- **repository**: Contém as interfaces de repositório para acesso ao banco de dados.
- **enums**: Contém os enumeradores utilizados no projeto.

## Funcionalidades Implementadas

### Usuários
- **Cadastro de Usuários**: Endpoint para criar novos usuários. A data de criação é automaticamente registrada.

## Configuração do Banco de Dados

O projeto utiliza o banco de dados em memória H2 para facilitar o desenvolvimento e os testes. As configurações estão no arquivo [`application.properties`].

- URL do banco de dados: `jdbc:h2:mem:database`
- Console H2 habilitado: `/h2-console`
- Usuário: `admin`
- Senha: `admin`

## Dependências Principais

As dependências principais do projeto estão listadas no arquivo [`pom.xml`](pom.xml):

- **Spring Boot Starter Web**: Para criação de APIs RESTful.
- **Spring Boot Starter Data JPA**: Para integração com o banco de dados.
- **H2 Database**: Banco de dados em memória.
- **Lombok**: Para reduzir o boilerplate no código.
- **Spring Boot DevTools**: Para facilitar o desenvolvimento com hot reload.

## Como Executar o Projeto

1. Certifique-se de ter o Java 24 instalado.
2. Clone este repositório.
3. Execute o comando abaixo para iniciar o projeto:
   ```sh
   ./mvnw spring-boot:run

> w/ Copilot