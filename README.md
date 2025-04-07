![img](https://raw.githubusercontent.com/FKouto/Habit-Track/refs/heads/main/header-repo-img.png)
# 🧠 Habit Tracker – Sistema de Gestão de Hábitos

Este repositório reúne a **aplicação web (front-end)** e a **API RESTful (back-end)** do projeto Habit Tracker, desenvolvido como parte de um trabalho acadêmico da disciplina de **Gestão e Qualidade de Software**. O objetivo do sistema é permitir que usuários cadastrem, editem e acompanhem seus hábitos diários de forma prática.

# Integrantes
- Scrum Master: <br>
    - Enzo Nascimento (202320732). <br>
- Front-End: <br>
    - William Damascera (2022512580) / (Tech lead); <br>
    - Lucas Alves; Kauan Lacerda e Silva (202412124). <br>
- Back-End:
    - Felipe Couto (823127403) / (Tech Lead); <br>
    - Geziel Oliveira Silva (202313071);<br>
    - Fabio Moreira. <br>
- Banco de Dados: <br>
    - Geziel Oliveira Silva (202313071); <br>
    - Lorhan de Souza Mendes Ferreira (1262323379). <br>
- QA: <br>
    - João Vitor Mendes da Silva (202411713). <br>

## 📦 Tecnologias Utilizadas

### 🔙 Back-end
- **Java 21**
- **Spring Boot**
- **Maven**
- **H2 Database**
- **Spring Security**
- **Jakarta Validation**
- **BCrypt**
- **JWT**

### 🔜 Front-end
- **Angular 17**
- **TypeScript**
- **PrimeNG**
- **Node.js 18.13+**
## ✅ Funcionalidades
👤 Usuários
- Cadastro de novos usuários
- Login com geração de token JWT
- Exclusão de conta

📌 Hábitos
- Criação de novos hábitos
- Listagem dos hábitos do usuário autenticado
- Edição e exclusão de hábitos

## 🚀 Como Executar o Projeto Localmente
1. **Requisitos:**
    - Java 21+
    - Node.js 18.13+
    - Angular CLI 17+
    - Maven

## Clonar
```
git clone https://github.com/FKouto/Habit-Track.git
cd habit-tracker
```

## Run Back-end
```
cd backend
./mvnw spring-boot:run
```

## Run Front-end
```
cd frontend
npm install
ng serve
```

## Banco de Dados
O projeto usa o **H2 Database (em memória)** para facilitar testes e desenvolvimento.
