#!/bin/bash

set -x # Print commands and their arguments as they are executed.

cd /opt/
docker-compose -f docker-compose-prod.yml pull
docker-compose -f docker-compose-prod.yml down
docker-compose -f docker-compose-prod.yml up -d couch
sleep 50
./configure-node.sh 
docker-compose -f docker-compose-prod.yml up -d app
docker-compose -f docker-compose-prod.yml up -d app-ng
