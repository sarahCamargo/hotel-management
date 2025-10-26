<h1 align="center"> API de Gerenciamento de Hotel </h1>

<p align="center">
  <img src="https://img.shields.io/badge/java-21-blue" alt="java version" />
  <img src="https://img.shields.io/badge/spring--boot-3.5.7-brightgreen" alt="spring boot version"/>
  <img src="https://img.shields.io/badge/postgresql-16-blue" alt="postgresql" />
  <img src="https://img.shields.io/badge/docker-ready-informational" alt="docker"/>
</p>

# Índice 

* [Funcionalidades](#funcionalidades)
* [Como utilizar o projeto](#como-utilizar-o-projeto)
  * [Clonando o Repositório](#clonando-o-repositório)
  * [Subindo o ambiente com Docker](#subindo-o-ambiente-com-docker)
    * [Pré-requisitos](#pré-requisitos)
    * [Subindo o Projeto](#subindo-o-projeto)
    * [Acessando a aplicação](#acessando-a-aplicação)
* [Testando a Aplicação com JUnit](#testando-a-aplicação-com-junit)
* [Pontos de Melhoria do Projeto](#pontos-de-melhoria-do-projeto)

# Funcionalidades

- `Gerenciamento de Hóspedes`: Cadastro, atualização, listagem e exclusão de hóspedes.
- `Gerenciamento de Reservas`: Criação, alteração, cancelamento e exclusão de reservas.
  - `Cálculo` automático de valores conforme período e adicionais (ex: garagem).
  - `Validações` de sobreposição de reservas e integridade de dados.
- `Controle de Estadias`: Realização de check-in e check-out.
  - `Cálculo` automático do valor final conforme horário de saída e tipo de tarifa (dia útil / fim de semana).
- `Documentação da API`: Integração com Swagger UI para navegação e teste dos endpoints.

# Como utilizar o projeto

## Clonando o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/sarahCamargo/hotel-management.git
cd hotel-management
```

## Configurando o ambiente com .env

Crie um arquivo chamado `.env` na raiz do projeto com o seguinte conteúdo e insira o usuário e senha do banco.

```bash
# PostgreSQL
POSTGRES_DB=hoteldb
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_PORT=5432

# App
APP_PORT=8080

# Spring Datasource
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/hoteldb
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

# Environment
SPRING_PROFILES_ACTIVE=dev

```

## Subindo o ambiente com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Hub]()

### Subindo o Projeto
```bash
docker-compose up --build
```

### Acessando a Aplicação
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html


# Testando a Aplicação com JUnit
```bash
./mvnw test
```

# Pontos de Melhoria do Projeto

- Implementar Spring Security + JWT para autenticação e autorização.
- Adicionar cache (Redis) para otimizar consultas.
- Integrar RabbitMQ para notificações assíncronas (check-in, check-out, cancelamentos).
- Adicionar logs estruturados e monitoramento com Spring Boot Actuator.
