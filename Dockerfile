# Use an official base image with Java and Maven 3.8.6
FROM maven:3.8.6-openjdk-11

# Set the working directory to the location of the JHipster app
WORKDIR /app

# Copy the entire application directory to the container
COPY procesador-ordenes-app/ .


# Expose the port that your JHipster app is running on in dev mode
EXPOSE 8080

# Specify the command to run your JHipster app in dev mode
CMD ["./mvnw"]
