# Key Lime Box's Simple MQ

Why another Message Queue - because we needed something simple that does message 
publish/subscribe the way we wanted it. We looked at all the _established_ message 
queue systems. They are good, but they are also very complex, handle many messaging
needs, etc.

Most publish/subscribe model we found available were based on _connected subscribers_. We 
wanted persistent messages to subscribers whether connected or not.

# Basic Architecture

We have a web server providing the REST API. This is a Spring Boot (Spring 4) based web 
application.

The configuration of queues and persistence of messages and configuration is through 
MongoDB.

## Operations

When a message is published to a queue, Simple MQ looks at the queue's subscriber and creates
a queued message for each subscriber and persist them. If there are no subscriber to the 
queue, no messages are queued.

## Persistence

In this initial implementation, the persistence to MongoDB is done in a very simple way. 
There is a collection named `messages` in which the `QueuedMessage`s are stored.

Each `QueuedMessage` contains the ID of the queue they are for as well as the subscriber
they are for.

### Idea of Improvement

An idea on how this could be improved would be to dynamically create collections for each
queue/subscriber pair. The advantages of doing it this way are:

 - Less overhead on the storage when reading queued messages - no lookup required to filter
   the entries in the `messages` collection to those applicable to the queue/subscriber
   requested.  

 - Adds flexibility to the management of the queues - one could define a queue/subscriber
   collection to be a *circular buffer* so that even if the entries aren't read, the queue
   does not grow indefinitely.

There are also some downside to this approach:

 - Harder to manage the state of the queue - you have to query each collections in the 
   storage to find how much entries they each have, if they have been there for too long,
   etc. When using a single collection for all messages, one can just query with aggregates.

 - Can't use the Spring Data *repository* API and therefore require additional code for
   the storage persistance.

# REST API

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

A message can be published by posting a payload object (JSON) to this REST service with
the following parameters:

- `publisher`: The ID of the publisher. 

A simple `String` payload can also be sent using a request parameter called `payload`, 
which is more convenient for payloads which consist of an ID for example.

*Note*: if there are no subscriber to a queue, the messages published will not be 
recorded.

### Retrieving the Next Message

GET `/api/queues/{queueId}/next`

Parameters:
 - `subscriber`: The ID of the subscriber to get the message for.
 - `previous`: (Optional) The ID of the previous message to be removed before getting the next one.

