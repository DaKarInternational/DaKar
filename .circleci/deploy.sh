#!/usr/bin/env bash

cd /opt/
docker-compose pull
docker-compose down
docker-compose up -d couch
sleep 20
./configure-node.sh 
docker-compose up -d app
