#!/bin/bash
docker rm -f jjdd5-jbusters_db_1
docker rm -f jjdd5-jbusters_app_1
docker-compose up -d --build --force-recreate
docker stop jjdd5-jbusters_app_1
sleep 120
docker start jjdd5-jbusters_app_1

