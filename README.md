📚 Projeto:  Sistema de Gerenciamento de Livros com Spring Boot, PostgreSQL e API Gutendex

Sistema Java desenvolvido com Spring Boot que permite gerenciar livros de domínio público por meio da integração com a API Gutendex e persistência de dados no banco PostgreSQL. O projeto é executado via terminal e possui cinco funcionalidades principais, oferecendo uma experiência prática de busca, listagem e filtragem de livros e autores.

Este sistema simula uma aplicação real de cadastro e consulta de obras literárias, promovendo boas práticas de desenvolvimento backend com foco em consumo de APIs, persistência com JPA, organização por camadas, e uso de containers com Docker para desenvolvimento e produção.

⚙️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **API REST (Gutendex)**
* **Docker e Docker Compose**
* **IntelliJ IDEA**
* **Maven**

🧩 Funcionalidades

1. 🔍 Buscar livro pelo título (API Gutendex + banco de dados)
   O sistema consulta a API Gutendex a partir do título informado, exibe os dados no terminal e permite salvar o livro diretamente no banco de dados local.

2. 📘 Listar livros registrados (Somente banco de dados)
   Exibe todos os livros cadastrados anteriormente no banco, incluindo título, autor, idioma e número de downloads.

3. 🧑‍💼 Listar autores registrados
   Lista todos os autores únicos encontrados nos livros cadastrados.

4. 📆 Listar autores vivos em determinado ano
   Filtra autores que estavam vivos no ano informado com base em suas datas de nascimento e falecimento.

5. 🌐 Listar livros por idioma
   Permite buscar livros cadastrados a partir do idioma (PT, EN, ES, FR), retornando os livros que correspondem à linguagem.


🗃️ Estrutura do Projeto

* `controller/` – Gerencia a entrada do usuário via terminal.
* `service/` – Regras de negócio, integração com a API Gutendex e persistência.
* `model/` – Entidades JPA (Livro, Autor).
* `repository/` – Interfaces do Spring Data JPA.
* `config/` – Configurações adicionais (se necessário).
* `resources/application.properties` – Configuração do banco de dados.
* `docker/` – Arquivos para containerização e scripts auxiliares.


🐳 Docker (Opcional)

O projeto conta com arquivos `Dockerfile`, `docker-compose.yml` e scripts auxiliares para rodar o sistema em containers Docker, incluindo:

* Container da aplicação
* Banco de dados PostgreSQL
* pgAdmin para gerenciamento do banco


📈 Objetivos de Aprendizado

* Prática com consumo de APIs públicas em Java.
* Aprender a persistir dados usando Spring Data JPA.
* Criar um sistema de linha de comando orientado a dados.
* Trabalhar com datas, filtros, enums e relacionamento entre entidades.
* Configuração e uso de Docker para ambientes de desenvolvimento e produção.

Se você tiver dúvidas ou precisar de ajuda, consulte as instruções no código-fonte ou entre em contato com os seguintes canais:

Link do repositório no GitHub Para questões técnicas, envie um email para: loisee.dev@gmail.com Para discussões sobre o projeto, utilize a seção de Issues no GitHub. Autores do projeto Emilli Loise Moraes Campos
