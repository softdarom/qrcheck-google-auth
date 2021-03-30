FROM openjdk:11.0.10-jdk

ARG HOME=/opt/google-auth
ARG JAR=google-auth.jar

ENV TZ=Europe/Moscow

WORKDIR $HOME
COPY build/libs/google-auth.jar $HOME/$JAR
ENTRYPOINT java $JAVA_OPTS -jar $JAR