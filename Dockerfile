FROM adoptopenjdk/openjdk8
ADD target/pms-api-gateway.jar pms-api-gateway.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","pms-api-gateway.jar"]