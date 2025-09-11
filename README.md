# 📋 Lista de Tarefas (To-Do List)

Aplicação fullstack para gerenciamento de tarefas, onde é possível **adicionar, editar, concluir e excluir tarefas**. As tarefas concluídas aparecem riscadas e ao final da lista, enquanto as pendentes aparecem primeiro.


## 🚀 Imagem do Projeto

### 🖥️ Frontend
<img width="869" height="880" alt="Captura de tela 2025-09-11 104953" src="https://github.com/user-attachments/assets/ce42ad28-3e77-4c93-bc63-794a42886cf0" />


### 🛠️ Backend

<img width="1684" height="711" alt="Captura de tela 2025-09-10 113348" src="https://github.com/user-attachments/assets/daead5ed-f659-42c7-a2ba-62cf8b88ec78" />

## 🚀 Tecnologias

### Frontend
- [React.js]
- [Vite]
- [Axios]

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate Validator
- PostgreSQL ou H2 (ambiente de testes)
- Lombok
- JUnit 5 + Mockito
- Swagger (Springdoc OpenAPI)
- Gradle

## ⚙️ Instalação e Execução

### 1️⃣ Clonar o Repositório

bash
git clone https://github.com/JacquelineCasali/fullstack1
cd fullstack1

### 2️⃣ Backend
cd jtech-tasklist-backend
./gradlew bootRun

Acesse: http://localhost:8080

A API está documentada com Swagger em:
http://localhost:8080/swagger-ui.html

### 3️⃣ Frontend
cd jtech-tasklist-frontend
npm install
npm run dev
Acesse: http://localhost:5173

## 📌 Funcionalidades

- ✅ Criar nova tarefa
- ✅ Editar título e descrição
- ✅ Marcar como concluída (fica riscada e vai para o final da lista)
- ✅ Excluir tarefa
- ✅ Ordenar pendentes primeiro

## 🎨 Interface

- Tarefas pendentes aparecem primeiro
- Tarefa concluída: destaque em verde
- Tarefa pendente: destaque em vermelho

## 📖 Regras de Negócio

- Título da tarefa é obrigatório (5 a 100 caracteres)
- Status é obrigatório: PENDENTE ou CONCLUIDA
- Não é permitido cadastrar duas tarefas com o mesmo título e status PENDENTE

## 🧪 Testes Automatizados

- Cobertura de regras de negócio no serviço de tarefas
- Ferramentas: JUnit 5 e Mockito
- Executar os testes:
./gradlew test

## 💡 Decisões Técnicas

- ✅ Banco H2: Utilizado localmente por facilitar testes sem necessidade de instalação
- ✅ PostgreSQL: Banco de produção, mais robusto
- ✅ Spring Boot: Facilita desenvolvimento de APIs REST com segurança, validação, JPA, etc.
- ✅ Swagger: Documentação automática para facilitar testes e integração
- ✅ Lombok: Redução de código repetitivo (getters/setters)

## 🚧 Melhorias Futuras

- ✅ Autenticação e autorização com JWT
- ✅ Filtros de busca (por título/status)
- ✅ Paginação dos resultados
- ✅ Upload de arquivos/anexos
- ✅ Monitoramento com Spring Boot Actuator
- ✅ Deploy em ambiente cloud

## 📄 Licença

Projeto desenvolvido por Jacqueline Casali.
https://www.linkedin.com/in/jaquelinecasali/
