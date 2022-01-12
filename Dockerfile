FROM maven as build-stage
WORKDIR /app
COPY . .

RUN mvn -B clean package -Dmaven.test.skip=true -Dautoconfig.skip -P prod
RUN ls /app



FROM openjdk:8
WORKDIR /app
COPY --from=build-stage /app/ThinkSpringbootV2/target/ThinkSpringbootV2-0.0.1-SNAPSHOT.jar /app/ThinkSpringbootV2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/bin/sh", "-c", "java -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -jar /app/ThinkSpringbootV2-0.0.1-SNAPSHOT.jar"]
