#!/bin/bash
set -e

echo "Buildando aplicação Spring Boot..."
mvn clean package -DskipTests

echo "Buildando imagem Docker da API..."
docker-compose build api-books

echo "Subindo banco de dados, banco do Kong e migrations..."
docker-compose up -d postgres kong-database kong-migrations

# Espera migrations
until [ "$(docker inspect -f '{{.State.Status}}' kong-migrations)" == "exited" ]; do
  >&2 echo "Aguardando Kong Migrations finalizar..."
  sleep 2
done

# Verifica exit code das migrations
if [ "$(docker inspect -f '{{.State.ExitCode}}' kong-migrations)" != "0" ]; then
  echo "Kong migrations falhou!"
  docker logs kong-migrations
  exit 1
fi

echo "Subindo Kong e API..."
docker-compose up -d kong api-books

# Espera Kong subir 
timeout=60
elapsed=0
while ! curl -s http://localhost:8001 >/dev/null; do
  if [ "$elapsed" -ge "$timeout" ]; then
    echo "Timeout: Kong não iniciou no tempo esperado."
    exit 1
  fi
  >&2 echo "Aguardando Kong subir..."
  sleep 2
  elapsed=$((elapsed + 2))
done

echo "Configurando Kong..."

curl -i -X POST http://localhost:8001/services/ \
  --data "name=api-books" \
  --data "url=http://api-books:8080"

#  Criar rota
curl -i -X POST http://localhost:8001/routes/ \
  --data "service.name=api-books" \
  --data-urlencode "paths[]=/api" \
  --data "strip_path=false"

curl -i -X POST http://localhost:8001/services/api-books/plugins \
  --data "name=key-auth" \
  --data "config.key_names=apikey"

curl -i -X POST http://localhost:8001/consumers/ \
  --data "username=cliente"

curl -i -X POST http://localhost:8001/consumers/cliente/key-auth \
  --data "key=12345"

#  Request transformer para remover 'id' do payload
curl -i -X POST http://localhost:8001/services/api-books/plugins \
  --data "name=request-transformer" \
  --data "config.remove.body=id"

echo -e "\n Kong configurado com sucesso!"
echo "API Key: 12345"
echo "URL base via Kong: http://localhost:8000/api"
