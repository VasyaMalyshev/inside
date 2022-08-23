ARG BUILDDIR=/data
ARG APP_PREFIX=inside

FROM maven:3.6.3-openjdk-17 AS builder
ARG BUILDDIR
RUN mkdir $BUILDDIR
RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository
WORKDIR $BUILDDIR
COPY src $BUILDDIR/src
COPY mvnw mvnw.cmd pom.xml $BUILDDIR/
RUN mvn dependency:resolve
RUN mvn package

#
FROM openjdk:17
ARG BUILDDIR
ARG APP_PREFIX
VOLUME /tmp
VOLUME /data
ENV TZ Europe/Moscow
ARG JAR_FILE
COPY --from=builder ${BUILDDIR}/target/${APP_PREFIX}-*.jar app.jar
EXPOSE 8075 8983
ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar