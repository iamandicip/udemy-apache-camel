# Apache Camel course on Udemy

Keywords:

- Apache Camel
- Java Spring
- Docker
- Active MQ

## Apache Camel

- Open source Enterprise integration framework
  - easily integrate systems consuming or producing data
- Inspired by [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/) book by Gregor Hohpe and Bobby Woolf
  - evolved to Microservice Architectures and Cloud
- Lean - lightweight and extensible
  - component architecture
  - supports many protocols, transports and data formats
  - supports many databases, message queues, APIs, cloud integrations, etc
  - provides Domain Specific Language (DSL) for application integration

## Camel Terminology

### Camel context - (0..n) Routes + Components +  ..., etc

- **Endpoint** - reference to a queue, database or a file
- **Route** - Endpoint + Processor(s) + Transformer(s)
- **Components** - Extensions (Kafka, JSON, JMS, etc)
- **Tranformation**:
  - data format transformation - XML to JSON
  - data type transformation - String to CurrencyConversionBean
- **Message** - Body + Headers + Attachments
- **Exchange** - Request + Response
  - Exchange id
  - Message Exchange Pattern (MEP) - InOnly / InOut
  - Input message and (Optional) Output message

## Docker commands

```bash
# start docker container
$ docker run -p 5000:5000 -d in28min/todo-rest-api-h2:latest
# show container log
$ docker logs -f <container_id>
# shor running containers
$ docker container ls -a
# show downloaded images
$ docker images
# show history of an image
$ docker image history <image_id>
# show image properties: ports, commands, layers, etc
$ docker image inspect <image_id>
# remove an image from local machine
$ docker image remove <image_id>
# create and run a docker container - identical effect with 1st command
$ docker container run -p 5000:5000 -d in28min/todo-rest-api-h2:latest
# stop docker container - graceful shutdown - SIGTERM
$ docker container stop <container_id>
# pause a container
$ docker container pause <container_id>
# unpause a docker container
$ docker container unpause <container_id>
# inspect docker container properties
$ docker container inspect <container_id>
# remove all stopper containers
$ docker container prune
# show container logs
$ docker container logs -f <container_id>
# kill container - force shutdown - SIGKILL
$ docker container kill <container_id>
# restart container on docker daemon restart
$ docker container run -p 5000:5000 -d --restart=always in28min/todo-rest-api-h2:latest
# show docker related events
$ docker events
# show running processes of a container
$ docker top <container_id>
# show stats of all containers running
$ docker stats
# start container with specified memory, cpu quota (100000 = 100%)
$ docker container run -p 5000:5000 -d -m 512m --cpu-quota 5000 in28min/todo-rest-api-h2:latest
# show docker disk space
$ docker system df
```

## ActiveMQ

```bash
$ docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```

## Camel - Terminology

- Camel Context (0..n) Routes + Components + ...
  - Endpoint - Reference to a queue, database or file
  - Route - Endpoints + Processor(s) + Transformer(s)

[Simple language Camel](https://camel.apache.org/components/3.18.x/languages/simple-language.html)

## Enterprise Integration Patterns

- **Pipeline** - the default in Camel - `pipeline()`
- **Content based routing** - `choice()`
- **Multicast** - send the same message to multiple endpoints - `multicast()`
- **Splitter** - split a single message into multiple messages - `split()`
- **Aggregate** - combine multiple messages into a single message - `aggregate()`
- **Routing slip** - similar to multicast, but the logic to which endpoint we send is dynamic - `routingSlip()`
- **Dynamic route** - after each step, we decide which endpoints to execute `dynamicRouter()`

## Other referehces

- [Why Camel?](https://camel.apache.org/manual/latest/faq/why-the-name-camel.html)
- [Camel Examples](https://github.com/apache/camel-examples/tree/master/examples)
- [Camel Spring Boot Configuration](https://camel.apache.org/camel-spring-boot/latest/spring-boot.html)
- [Complete Spring Boot Starters List](https://camel.apache.org/camel-spring-boot/latest/list.html)
- [Camel Spring Boot Examples](https://github.com/apache/camel-spring-boot-examples)
- [Enterprise Integration Patterns](https://camel.apache.org/components/latest/eips/enterprise-integration-patterns.html)
