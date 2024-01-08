# Como rodar o projeto?
1 - Suba o Docker na sua máquina. <br/>
2 - Rode o Script: "Run.ps1", esse Script irá efetuar todas configurações necessárias para rodar o projeto.<br/>
3 - Seja feliz.

# Como utilizar os microserviços?
1 - Acesse o Keycloak "http://localhost:8081", conecte-se com o usuário/senha: admin/admin.
  1.1 - Crie um Realm do seu critério, vamos utilizar ele para autenticação.
  1.2 - Com o Realm criado, crie um Client dentro desse Realm.
  1.3 - Com o Client criado, altere o método de autenticação para Confidential e habilite a opção: "Authorization Enabled".
  1.4 - Salve e pronto, tudo configurado por aqui.
2 - Acesse o RabbitMq "http://localhost:15672/#/queues", conecte-se com o usuário/senha: guest/guest
  2.1 - Crie uma fila com o nome: "emissao-cartoes"
3 - Tendo o Insominia ou o Postman em sua máquina, faça a requisição para obter o TOKEN para o Keycloak.
  3.1 - O Endpoint do Keycloak para geração de Token é: "http://localhost:8081/realms/{seu_realm}/protocol/openid-connect/token", apenas altere na parte da URL para o Realm que você criou!
  3.2 - Utilize o "Form URL Encoded" para passar os atributos necessários na geração do Token.
  3.3 - Defina os atributos:
    3.3.1 - grant_type = client_credentials
    3.3.2 - client_id = {seu_client}
    3.3.3 - client_secret = {seu_secret}
  3.4 - Após seguir os passos anteriores será possível obter o Token e utilizar ele nos outros endpoints.
4 - A partir desse momento, com o Token na mão, basta utilizar ele nos microserviços e utilizar. As rotas padrões são:
  4.1 - POST - http://localhost:8080/clientes
    4.1.1 - Body - { "nome" : "Gabriel Almeida","cpf": "00000000000","idade": 22 }
  4.2 - POST - http://localhost:8080/cartoes
    4.2.1 - Body - { "nome": "bradesco mastercard","bandeira": "MASTERCARD","renda": 5000,"limite": 8000 }
  4.3 - POST - http://localhost:8080/avaliacoes-credito/solicitacoes-cartao
    4.3.1 - Body - { "idCartao": 1,"cpf": "00000000000","endereco": "Uma Rua em Algum Lugar","limiteLiberado": 17600.000 }
