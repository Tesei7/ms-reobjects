# How to run re-create docker image

**mvn clean package docker:build**

# To run image

## Create network

**docker network create ms-net**

## Run image

**docker run
  -p 8080:8080
  --env CONFIGSERVER_PORT=8888
  --env PROFILE=dev
  --env CONFIGSERVER_URI=http://ms-confserver:8888
  --env ENCRYPT_KEY=\*\*\*\*\*\*
  --env EUREKASERVER_URI=http://ms-eureka:8761/eureka/
  --env EUREKASERVER_PORT=8761
  --name ms-reobjects
  --network ms-net
  tesei7/ms-reobjects:latest**