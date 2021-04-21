FROM openjdk:11.0.10-jdk

ARG APP_HOME=/opt/google-auth
ARG APP_JAR=google-auth.jar

ENV TZ=Europe/Moscow \
    HOME=$APP_HOME \
    JAR=$APP_JAR

WORKDIR $HOME
COPY build/libs/google-auth.jar $HOME/$JAR
ENTRYPOINT java $JAVA_OPTS -jar $JAR