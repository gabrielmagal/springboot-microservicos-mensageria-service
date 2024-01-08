Write-Host "Iniciando compilacao do projeto!"
Set-Location ./msservices

Write-Host "Executando a compilacao do projeto"
mvn clean install -DskipTests
Write-Host "Compilacao concluida com sucesso!"

Write-Host "Criando uma network padrao para todos!"
docker network create conexao-network
Write-Host "Criacao de network feita com sucesso!"

Write-Host "Executando Rabbitmq!"
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
Write-Host "Concluido Rabbitmq!"

Write-Host "Executando Keycloak!"
docker run -d --name keycloak -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin --network conexao-network quay.io/keycloak/keycloak:18.0.0 start-dev
Write-Host "Concluido Keycloak!"

Write-Host "Executando Eureka!"
Set-Location ./eurekaserver
docker build --tag eureka-server .
docker run -d --name eureka-container -p 8761:8761 --network conexao-network eureka-server
Write-Host "Concluido Eureka!"

Write-Host "Executando Clientes!"
Set-Location ../msclientes
docker build --tag msclientes .
docker run -d --name msclientes-container --network conexao-network -e ENV_EUREKA_SERVER=eureka-container msclientes
Write-Host "Concluido Clientes!"

Write-Host "Executando Cartoes!"
Set-Location ../mscartoes
docker build --tag mscartoes .
docker run -d --name mscartoes-container --network conexao-network -e ENV_EUREKA_SERVER=eureka-container -e ENV_RABBITMQ_SERVER=rabbitmq mscartoes
Write-Host "Concluido Cartoes!"

Write-Host "Executando AvaliadorCartoes!"
Set-Location ../msavaliadorcredito
docker build --tag msavaliadorcredito .
docker run -d --name msavaliadorcredito-container --network conexao-network -e ENV_EUREKA_SERVER=eureka-container -e ENV_RABBITMQ_SERVER=rabbitmq msavaliadorcredito
Write-Host "Concluido AvaliadorCartoes!"

Write-Host "Executando CloudGateway!"
Set-Location ../mscloudgateway
docker build --tag mscloudgateway .
docker run -d --name mscloudgateway-container --network conexao-network -e ENV_EUREKA_SERVER=eureka-container -e ENV_KEYCLOAK_SERVER=localhost -e ENV_KEYCLOAK_PORT=8081 mscloudgateway
Write-Host "Concluido CloudGateway!"
Write-Host "Script concluido com sucesso!"