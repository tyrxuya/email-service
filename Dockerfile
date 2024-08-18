# Use the official OpenJDK image as the base image
FROM openjdk:21

# Set the working directory within the container
WORKDIR /email

# Copy the packaged JAR file from the target directory into the container
COPY rest/target/email.jar email.jar
# Expose the port that your application runs on
EXPOSE 8086

# Define the command to run your application
CMD ["java", "-jar", "email.jar"]