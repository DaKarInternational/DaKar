#!/usr/bin/env bash

cd /opt/
docker-compose pull
docker-compose down
docker-compose up
