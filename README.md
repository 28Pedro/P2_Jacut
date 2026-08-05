# 🚀 Jackut

Backend de uma rede social inspirada no Orkut, desenvolvido como projeto da disciplina de Programação II (P2), com foco na aplicação de conceitos de Programação Orientada a Objetos e Desenvolvimento Orientado a Testes (TDD).

---

## 📋 Sobre o Projeto

O **Jackut** é uma aplicação backend que simula as principais funcionalidades de uma rede social, permitindo o gerenciamento de usuários, amizades, comunidades e interações entre os participantes.

O projeto foi desenvolvido com ênfase em boas práticas de engenharia de software, utilizando testes automatizados para validar os requisitos do sistema durante todo o processo de desenvolvimento.

---

## ✨ Funcionalidades

- 👤 Cadastro e gerenciamento de usuários
- 🤝 Solicitação e gerenciamento de amizades
- 👥 Criação e gerenciamento de comunidades
- 📄 Gerenciamento de perfis
- ✅ Validação das regras de negócio
- 💾 Persistência dos dados
- 🧪 Testes automatizados dos casos de uso

---

## 🛠 Tecnologias Utilizadas

- Java
- Programação Orientada a Objetos (POO)
- EasyAccept
- JUnit
- XML (persistência de dados)

---

## 🏗 Arquitetura

O projeto foi estruturado seguindo uma arquitetura em camadas, separando responsabilidades entre:

- Interface de testes (EasyAccept);
- Camada de fachada (Facade);
- Regras de negócio;
- Gerenciamento de dados;
- Persistência em arquivos XML.

---

## 📂 Estrutura do Projeto

```text
.
├── src/
│   ├── facade/
│   ├── business/
│   ├── persistence/
│   ├── entities/
│   └── exceptions/
├── test/
├── xml/
└── README.md
```

---

## ⚙️ Requisitos

- Java 17 ou superior
- Maven
- EasyAccept

---

## 🚀 Como Executar

1. Clone o repositório.

```bash
git clone https://github.com/usuario/jackut.git
```

2. Compile o projeto.

```bash
mvn clean install
```

3. Execute os testes automatizados.

```bash
mvn test
```

---

## 🧪 Testes

O projeto foi desenvolvido utilizando **EasyAccept**, uma ferramenta voltada para testes de aceitação, permitindo validar os requisitos funcionais da aplicação por meio de cenários automatizados.

Além disso, foram aplicados princípios de desenvolvimento orientado a testes (TDD), garantindo maior confiabilidade e qualidade do código.

---

## 🎯 Objetivos de Aprendizagem

Durante o desenvolvimento deste projeto foram aplicados conceitos como:

- Programação Orientada a Objetos;
- Encapsulamento e abstração;
- Herança e polimorfismo;
- Tratamento de exceções;
- Persistência de dados;
- Desenvolvimento Orientado a Testes (TDD);
- Testes de Aceitação com EasyAccept;
- Organização de software em camadas.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos na disciplina de Programação II.
