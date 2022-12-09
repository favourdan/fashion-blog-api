FROM openjdk:11
EXPOSE 8080
ADD ./target/fashion-blog-api-0.0.1-SNAPSHOT.jar fashion-blog-api.jar
ENTRYPOINT ["java","jar","/fashion-blog-api.jar"]
