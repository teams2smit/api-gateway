FROM openjdk:8
ADD target/pmsapigateway.jar pmsapigateway.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","pmsapigateway.jar"]