#!/bin/bash

./mvnw clean package

docker-compose -f docker/docker-compose.yml up --build