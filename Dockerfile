FROM maven:3.9.12-eclipse-temurin-8 AS DEPENDENCIES 
WORKDIR /opt/app
COPY pom.xml .
RUN mvn dependency:go-offline -B 

FROM maven:3.9.12-eclipse-temurin-8 AS BUILDER
WORKDIR /opt/app
COPY --from=DEPENDENCIES /opt/app /opt/app/
COPY --from=DEPENDENCIES /root/.m2 /root/.m2
COPY src /opt/app/src/
RUN mvn clean install -DskipTests

FROM tomcat:9.0.113-jre8-temurin-noble
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/ROOT
COPY --from=BUILDER /opt/app/target/*.war webapps/ROOT.war
EXPOSE 8080
