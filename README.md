# sblibrary
API library using spring boot and docker

# Gera executável da aplicação
./mvnw clean package -DskipTests

# Roda executável gerado
java -jar target/sblibrary-0.0.1-SNAPSHOT.jar

# Cria rede interna do docker
docker network create library-network

# Cria container com o banco
docker-compose -f docker-compose-db.yml up -d

# Teste de conexão com o banco
psql -h localhost -p 5433 -U postgresprod -d library

# Gerando imagem da aplicação com o Dockerfile
docker build --tag springboot/libraryapi .

# Gerando o container e rodando
docker run --name libraryapi-prod -e DATASOURCE_URL=jdbc:postgresql://librarydb-prod:5432/library -e DATASOURCE_USERNAME=postgresprod -e DATASOURCE_PASSWORD=postgresprod --network library-network -d -p 8080:8080 -p 9090:9090 springboot/libraryapi

# Ver os logs da aplicação
docker logs libraryapi-prod
