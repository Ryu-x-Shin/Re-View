#!/bin/bash

source /home/ubuntu/mytoyproject/DeploySettings.env

echo "------- start -------"

docker stop mytoyproject-server || true
docker rm mytoyproject-server || true
docker pull $ECR_URI/mytoyproject-server:latest
docker run -d --name mytoyproject-server -p 8080:8080 $ECR_URI/mytoyproject-server:latest

echo "------- end -------"