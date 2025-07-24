FROM amazoncorretto:21-alpine-jdk

RUN apk update && apk add --no-cache tzdata

RUN addgroup -S spring \
	&& adduser -S spring -G spring \
	&& mkdir -p /home/spring/slips \
	&& chown -R spring:spring /home/spring/slips	

USER spring:spring

COPY balance-api/target/*.jar app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "/app.jar"]