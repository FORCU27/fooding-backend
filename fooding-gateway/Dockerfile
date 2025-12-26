FROM eclipse-temurin:17-jdk

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY fooding-gateway/build/libs/*.jar application.jar

ARG SPRING_PROFILES_ACTIVE
ARG FOODING_API_URI

ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV FOODING_API_URI=$FOODING_API_URI

EXPOSE 8080

CMD ["java", "-jar", "application.jar"]
