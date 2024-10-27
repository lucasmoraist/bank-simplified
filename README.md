# bank-simplified

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Descrição

Este projeto é um sistema de transferência de dinheiro, que permite aos usuários transferirem
valores entre carteiras. Ele implementa funcionalidades de autenticação, autorização de transações e notificação.

## Funcionalidades

1. **Criação de Usuário**
   - Registro de novos usuários, incluindo comerciantes e usuários comuns.
   ````json
       {
          "fullName": "Exemplo de Usuário",
          "cpf": "12345678900",
          "email": "exemplo@dominio.com",
          "password": "Senha123",
          "userType": "COMMON"
       }
   ````
2. **Depósito em Carteira**
   - Permite que os usuários depositem um valor em suas carteiras.
   ````json
     {
       "amount": 100.00
     }
   ````
3. **Transferência entre Carteiras**
   - Transferência de valores entre usuários, com verificação de saldo e autorização externa.
   ````json
      {
         "payerId": 1,
         "payeeId": 2,
         "amount": 100.00
      }
   ````
4. **Comunicação com Serviço Externo**
   - Integração com um serviço externo para autorização de transações financeiras.
5. **Notificação de Transações**
   - Envio de notificações aos usuários após a conclusão de transferências.
6. **Documentação da API**
   - Link para acessar a documentação na máquina -> http://localhost:8080/

## Instruções de Instalação
### Pré-requisitos
- Java 17 ou superior
- Maven 3.2.5 ou superior
- Docker

### Etapas
1. Clone o repositório na sua máquina
   ````bash
   git clone https://github.com/lucasmoraist/bank-simplified.git
   ````
2. Acesse o diretório do projeto
   ````bash
   cd bank-simplified
   ````
3. Suba o container do banco de dados com Docker Compose
   ````bash
   docker-compose up -d
   ````
## Instruções de Uso
- Utilize uma ferramenta como Postman ou Insomnia para testar os endpoints da API.
- Importe o arquivo de requisições disponível no diretório ./collection para facilitar os testes.
- É possível acessar a documentação da API na máquina local através do link http://localhost:8080/ e testar as requisições diretamente no Swagger.

## Contatos
<a href="mailto:seu-email@gmail.com"> <img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail"> </a>
<a href="https://www.linkedin.com/in/seu-linkedin/" target="_blank"> <img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"> </a>
