#!/usr/bin/env bash

cd /opt/
docker-compose pull
docker-compose down
docker-compose up -d couch
sleep 50
./configure-node.sh 
docker-compose up -d app
docker-compose up -d app-ng
