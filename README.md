# Key Lime Box's Simple MQ

Why another Message Queue - because we needed something simple that does message 
publish/subscribe the way we wanted it. We looked at all the _established_ message 
queue systems. They are good, but they are also very complex, handles many messaging
needs, etc.

Most publish/subscribe model we found available were based on _connected subscribers_. We 
wanted persistent messages to subscribers whether connected or not.

# Basic Architecture

We have a web server providing the REST API. This is a Spring Boot (Spring 4) based web 
application.

The configuration of queues and persistence of messages and configuration is through 
MongoDB.

# REST API

