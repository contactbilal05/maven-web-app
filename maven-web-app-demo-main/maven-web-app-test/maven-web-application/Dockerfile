# Use Tomcat as base image
FROM tomcat:9.0-jdk8-openjdk

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the WAR file to Tomcat
COPY target/range-rover.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

CMD ["catalina.sh", "run"]

