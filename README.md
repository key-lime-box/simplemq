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

## Queues

### Listing the Queues

GET `/api/queues`

This API call returns a list of the queues which exist.

### Creating a Queue

POST `/api/queues/new`

Applications that are going to be publishing messages must first create a queue. This is 
a one-time action which is normally done during the deployment of the application (i.e.
manually or through a configuration management system, not necessarily in the application's
code).

To create a queue, post the following parameters:

- `name`: A friendly queue name.
- `description`: A longer description as to what this queue is for.

### Subscribing to a Queue

POST `/api/queues/{queueId}/subscribe`

To subscribe to a queue, post the following parameters to this REST service:

- `subscriber`: The ID of the subscriber.


### Publishing a Message

POST `/api/queues/{queueId}/publish`

A message can be published by posting a payload object (JSON) to this REST service. 

*Note*: if there are no subscriber to a queue, the messages published will not be 
recorded.


## Publishers

### Viewing Existing Publishers

GET `/api/publishers`

Returns the list of existing publishers.

### Registering a New Publisher

POST `/api/publishers/new`

Before publishing messages, the applications need to be registered as publisher. This is
a one-time action which is normally done during installation, not at runtime.


## Subscribers

### Listing the Subscribers

GET `/api/subscribers`

Returns the list of all registered subscribers.

### Registering a New Subscriber

POST `/api/subscribers/new`

Before a subscriber can register with queues, it needs to be registered as a subscriber
so it gets its own ID. 

This is done by posting the following parameters to the above REST service:

- `name`: A friendly name for this subscriber.

This is normally done only once when an application is deployed either manually or by the
configuration management system. It is not something that generally gets put into the code
of the subscriber application.

