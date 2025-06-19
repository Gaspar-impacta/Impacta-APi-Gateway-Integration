#!/bin/bash
set -e

echo "Parando e limpando containers..."

docker-compose down --remove-orphans -v
