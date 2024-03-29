#!/bin/sh
#line breaks should be LF

echo "********************************************************"
echo "Waiting for the eureka server to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-eureka $EUREKASERVER_PORT`; do sleep 3; done
echo "******* Eureka Server has started ********"

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-confserver $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Starting ReObjects with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom                        \
     -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI       \
     -Dspring.cloud.config.uri=$CONFIGSERVER_URI                    \
     -Dspring.profiles.active=$PROFILE                              \
     -jar /usr/local/@project.artifactId@/@project.build.finalName@.jar
