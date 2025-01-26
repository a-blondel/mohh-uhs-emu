# Build image
FROM maven:3.9-ibm-semeru-21-jammy AS build
WORKDIR /usr/local/app
# Copy project into image
COPY ./ /usr/local/app/
# Build
RUN mvn clean package -DskipTests


# Run image
FROM ibm-semeru-runtimes:open-21-jre-jammy
# Copy jar
COPY --from=build /usr/local/app/target/mohh-uhs-emu-*.jar /mohh-uhs-emu.jar

EXPOSE 8080

# Start command
ENTRYPOINT ["java", "-jar", "/mohh-uhs-emu.jar"]
