#!/bin/bash
mvn clean package
docker rm -f jjdd5jbusters_db_1
docker rm -f jjdd5jbusters_app_1
docker-compose up -d --build --force-recreate
docker stop jjdd5jbusters_app_1
sleep 15
docker start jjdd5jbusters_app_1