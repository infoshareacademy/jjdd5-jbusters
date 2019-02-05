#!/bin/bash
mvn clean package
docker rm -f jjdd5-jbusters_db_1
docker rm -f jjdd5-jbusters_app_1
docker system prune -f --volumes
docker-compose up -d --build --force-recreate
docker stop jjdd5-jbusters_app_1
sleep 30
docker start jjdd5-jbusters_app_1
rm -r app/target
rm -r web/target
