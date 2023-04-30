FROM 10.60.110.23:8138/repository/applicationplatform-docker-hosted/maven-builder:3.8.6-openjdk-8-slim as builder
LABEL stage=builder
ARG MAVEN_PROXY
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD
ENV MAVEN_PROXY=$MAVEN_PROXY
ENV NEXUS_USERNAME=$NEXUS_USERNAME
ENV NEXUS_PASSWORD=$NEXUS_PASSWORD
RUN rm -rf /root/.m2
COPY . /app
RUN export MAVEN_PROXY=$MAVEN_PROXY
RUN export NEXUS_USERNAME=$NEXUS_USERNAME
RUN export NEXUS_PASSWORD=$NEXUS_PASSWORD
COPY ./settings.xml /app/.m2/settings.xml
WORKDIR /app
RUN mvn -s settings.xml clean install 
#RUN mvn clean install 
WORKDIR /app/target
CMD ["java","-jar","PodiotMockDevice-0.0.1-SNAPSHOT.jar"]
