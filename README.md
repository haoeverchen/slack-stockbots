# slack-stockbots
a bot integrating with slack to receive information of stocks


# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)



# Build img in your own computer
* under the path of this project, where Dockerfile is
## Build
```bash
$ docker build -t slack-stockbots .
```
## Run
```bash
$ docker run -d -p 8080:8080 slack-stockbots
```
## Logging
```bash
$ docker logs <Container ID or Name>
```

## Push image to your Docker Hub
```bash
$ docker login
```
```bash
$ docker tag slack-stockbots:latest aha86tw/slack-stockbots:latest
```
```bash
$ docker push aha86tw/slack-stockbots:latest
```
## linux/amd64 for kubernetes
```bash
$ docker buildx create --use
$ docker buildx build --platform linux/amd64 -t aha86tw/slack-stockbots:latest --push .
```
