FROM openjdk:8
EXPOSE 8080
ADD ./target/EtfOtpService-1.0.jar ./EtfOtpService-1.0.jar
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+0530","EtfOtpService-1.0.jar"]	