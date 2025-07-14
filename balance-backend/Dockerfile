FROM amazoncorretto:21-alpine-jdk

RUN apk update && apk add --no-cache tzdata

RUN addgroup -S spring \
	&& adduser -S spring -G spring \
	&& mkdir -p /home/spring/images \
	&& chown -R spring:spring /home/spring/images	

USER spring:spring

COPY balance-api/target/*.jar app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar", "/app.jar"]