
# TechVisit Backend

Bem-vindo ao repositório backend do **TechVisit**! Este projeto foi desenvolvido para gerenciar agendamentos de visitas técnicas na casa dos clientes. Siga as instruções abaixo para configurar e rodar o projeto localmente.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **PostgreSQL/MySQL**
- **JWT para autenticação**
- **Swagger** para documentação da API

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/)
- [Lombok](https://projectlombok.org/download)

## Clonando o Repositório

Para clonar o repositório, execute o seguintes comandos no terminal:

```bash
git clone https://github.com/matheusbruns/techvisit-back.git
```
```bash
cd techvisit-back
```
## Configurando o Banco de Dados

1. **Crie um banco de dados no PostgreSQL.**
2. **Configure o arquivo `application.properties`** para definir as credenciais e informações do banco de dados:

   ```properties
    spring.datasource.url=url_do_banco_de_dados
    spring.datasource.username=nome_do_banco_de_dados
    spring.datasource.password=senha_do_banco_de_dados
   ```

## Rodando o Projeto

Para rodar o projeto localmente, use o Maven para compilar e iniciar o servidor Spring Boot.

1. No terminal, execute o seguinte comando para compilar o projeto:

   ```bash
   mvn clean install
   ```

2. Em seguida, rode o projeto com o comando:

   ```bash
   mvn spring-boot:run
   ```

   O backend estará disponível em `http://localhost:8080`.

## Documentação da API

Acesse a documentação da API usando Swagger na seguinte URL:

```
http://localhost:8080/swagger-ui/index.html
```

## Testando o Projeto

### Endpoints Principais

1. **Autenticação e Autorizações** - Use o endpoint de autenticação para gerar um token JWT.
2. **Gestão de Clientes e Técnicos** - Cadastre e gerencie clientes e técnicos através dos endpoints específicos.

### Testes Unitários

Para rodar os testes unitários, execute o comando:

```bash
mvn test
```
