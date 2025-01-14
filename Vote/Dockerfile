FROM openjdk:22-jdk AS build
COPY . .
RUN mvn clean package -Dskiptests

FROM openjdk:22-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]