# Java Todo List CLI

## Visão Geral

O **Java Todo List CLI** é uma aplicação de linha de comando desenvolvida em Java que permite gerenciar uma lista de tarefas de forma eficiente. Com funcionalidades para adicionar, listar, atualizar e deletar tarefas, este projeto serve como uma excelente base para quem deseja aprender ou aprimorar habilidades com Java e desenvolvimento de CLIs.

## Estrutura do Projeto

A estrutura do projeto está organizada da seguinte forma:

```
.gitignore
.gradle/
.idea/
gradle/
src/
build.gradle
settings.gradle
README.md
```

- [cli](src\main\java\com\codemages\cli)
  : Contém o código-fonte principal da aplicação.

- [cli](src\main\java\com\codemages\cli): Contém os testes unitários.

- **build.gradle**: Script de configuração do Gradle.
- **settings.gradle**: Configurações de projeto do Gradle.
- **README.md**: Este arquivo de documentação.

## Requisitos

- **Java JDK 21**
- **Gradle 8.5**
- **IDE compatível** (ex: [IntelliJ IDEA](https://www.jetbrains.com/idea/))

## Instalação

1. **Clone o repositório:**

   ```sh
   git clone https://github.com/wesley-prado/java-todo-list-cli.git
   ```

2. **Navegue até o diretório do projeto:**

   ```sh
   cd task-cli
   ```

3. **Construa o projeto:**

   ```sh
   ./gradlew clean installDist build
   ```

## Como Usar

Para executar a aplicação, utilize o seguinte comando:

```sh
./build/install/task-cli/bin/task-cli [argumentos]
```

ou você pode adicionar no seu PATH para executar o comando em qualquer lugar do seu sistema.

```sh
export PATH=$PATH:/caminho/do/projeto/build/install/task-cli/bin
```

### Exemplos de Comandos

- **Adicionar uma tarefa:**

  ```sh
  task-cli add "Descrição da tarefa"
  ```

- **Listar todas as tarefas:**

  ```sh
  task-cli list
  ```

- **Listar tarefas em progresso:**

  ```sh
  task-cli list in-progress
  ```

- **Listar tarefas concluídas:**

  ```sh
  task-cli list done
  ```

- **Atualizar uma tarefa:**

  ```sh
  task-cli update 1 "Nova descrição da tarefa"
  ```

- **Deletar uma tarefa:**

  ```sh
  task-cli delete 1
  ```

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE.txt) para mais detalhes.

---

_Desenvolvido por Codemages_
