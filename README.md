# bank-simplified

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Descrição

Este projeto é um sistema de transferência de dinheiro, inspirado no PicPay, que permite aos usuários transferirem
valores entre carteiras. Ele implementa funcionalidades de autenticação, autorização de transações e notificação. A
aplicação está equipada com autenticação JWT e comunicação com um serviço externo de autorização de pagamento.

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
1. 
