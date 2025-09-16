# ⚖️ Advocacia-MVC — Mini ERP Jurídico

## 📌 Visão Geral
O **Advocacia-MVC** é um **mini ERP jurídico** para escritórios de advocacia, desenvolvido em **Java Spring Boot** e **Thymeleaf**, com **PostgreSQL/H2** como banco de dados.  

O sistema permite o gerenciamento completo de:
- **Funcionários**
- **Clientes**
- **Processos jurídicos**
- **Contratos**
- **Faturamentos**
- **Documentos**

Inclui **autenticação** e **autorização baseada em papéis** (`ADMIN` e `USER`), com controle de permissões em cada módulo.

---

## ⚙️ Tecnologias Utilizadas
- **Java 24**
- **Spring Boot 3.5.5**
  - Spring Web (MVC)
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **Thymeleaf** (Views HTML dinâmicas)
- **PostgreSQL - 17** (produção) / **H2** (desenvolvimento)
- **Maven**
- **Docker + Docker Compose**
- **Lombok**

---

## 📂 Estrutura do Projeto

```
advocacia-mvc/
 ├── docker-compose.yaml          # Containers (app + banco de dados)
 └── gerenciador/
     ├── pom.xml
     ├── src/main/java/com/unifil/advocacia/gerenciador/
     │   ├── GerenciadorApplication.java
     │   ├── auth/controller/       # Login e autenticação
     │   ├── security/              # Configurações de segurança
     │   ├── funcionario/           # Módulo Funcionários
     │   ├── cliente/               # Módulo Clientes
     │   ├── processo/              # Módulo Processos
     │   ├── contrato/              # Módulo Contratos
     │   ├── faturamento/           # Módulo Faturamentos
     │   ├── documento/             # Módulo Documentos
     │   └── exception/             # Exceções customizadas
     └── src/main/resources/
         ├── templates/             # Views Thymeleaf
         ├── application.properties
         └── application-dev.properties
```

---

## 🧩 Organização MVC

O projeto segue **Spring MVC** com separação clara de responsabilidades:

### 🔹 Model
Entidades JPA que representam as tabelas do banco:
- `funcionario/model/Funcionario.java`
- `cliente/model/Cliente.java`
- `processo/model/Processo.java`
- `contrato/model/Contrato.java`
- `faturamento/model/Faturamento.java`
- `documento/model/Documento.java`

### 🔹 Repository
Interfaces Spring Data JPA que encapsulam o acesso ao banco:
- `FuncionarioRepository.java`
- `ClienteRepository.java`
- `ProcessoRepository.java`
- `ContratoRepository.java`
- `FaturamentoRepository.java`
- `DocumentoRepository.java`

### 🔹 Service
Camada de lógica de negócio e regras:
- `FuncionarioService.java`
- `ClienteService.java`
- `ProcessoService.java`
- `ContratoService.java`
- `FaturamentoService.java`
- `DocumentoService.java`

### 🔹 Controller
Camada que recebe requisições HTTP, aciona os serviços e direciona as views:
- `FuncionarioController.java`
- `ClienteController.java`
- `ProcessoController.java`
- `ContratoController.java`
- `FaturamentoController.java`
- `DocumentoController.java`
- `AuthController.java` (autenticação/login)

### 🔹 DTO (Data Transfer Objects)
Objetos usados para entrada/saída de dados (criação/edição). Exemplos:
- `PostFuncionario.java`, `PutFuncionario.java`
- `PostCliente.java`, `PutCliente.java`
- `PostProcesso.java`, `PutProcesso.java`
- `PostFaturamento`, `PutFaturamento`
- `PostContrato`, `PutContrato`

### 🔹 View (Thymeleaf)
Templates que exibem os dados ao usuário:
- `templates/home.html`
- `templates/auth/login.html`
- `templates/funcionarios/` → `listar.html`, `form.html`, `editar.html`
- `templates/clientes/` → `listar.html`, `form.html`, `editar.html`
- `templates/processos/` → idem
- `templates/contratos/` → idem
- `templates/faturamentos/` → idem
- `templates/documentos/` → idem

---

## 🔐 Segurança
- Configuração em `security/SecurityConfig.java`
- Autenticação baseada em login/senha
- Roles:
  - **ADMIN** → CRUD completo
  - **USER** → acesso restrito (somente leitura)
- Implementação via `FuncionarioUserDetailsService.java`

---

## 🖥️ Funcionalidades

### Funcionários
- CRUD completo
- Atribuição de função (`Funcao`)

### Clientes
- Cadastro e gerenciamento de clientes

### Processos
- Cadastro de processos jurídicos com status e tipo (`StatusProcesso`, `TipoProcesso`)

### Contratos
- Registro de contratos e acompanhamento por status

### Faturamentos
- Controle de cobranças (`StatusFaturamento`)

### Documentos
- Upload e gerenciamento de documentos vinculados a processos

---

## 🚀 Como Executar

### 1. Clonar
```bash
git clone https://github.com/mateus-rcosta/advocacia-mvc.git
cd advocacia-mvc/gerenciador
```

### 2. Subir com Docker
```bash
docker-compose up -d
```

### 3. Executar localmente
```bash
mvn spring-boot:run
```

Aplicação disponível em: [http://localhost:8080](http://localhost:8080)
E-mail de administrador: admin@admin.com
Senha: admin
---

## 🛠️ Roadmap
- Relatórios financeiros e jurídicos
- Agenda de audiências
- Dashboard com indicadores
- Auditoria detalhada (logs de operações)
- Integração com APIs externas (ex.: tribunais)

---

## 📄 Licença
Este projeto é de uso acadêmico/profissional. Licença a definir.

---
## Feito por:

Heber Junior
Mateus Costa
Paulo Teodoro
