
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.web.controllers;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import java.util.List;

import org.keylimebox.simplemq.core.model.Queue;
import org.keylimebox.simplemq.core.model.QueuedMessage;
import org.keylimebox.simplemq.integration.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       QueuesController                                                        */
/**
 * Controller for the Message Queue API.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
@RestController
@RequestMapping ("/api/queues")
public class QueuesController
{

    /*==================================================================================*/
    /*===================================            ===================================*/
    /*=================================== Attributes ===================================*/
    /*===================================            ===================================*/
    /*==================================================================================*/

    /*==================================================================================*/
    /* Protected Attributes                                                             */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Private Attributes                                                               */
    /*==================================================================================*/

                /*======================================================================*/
                /* ATTRIBUTE: service                                                  */
                /**
                 * The service used to interact with the queues and subscribers.
                 */
                /*======================================================================*/
   @Autowired
   private QueueService             service;

    /*==================================================================================*/
    /* Class Attributes                                                                 */
    /*==================================================================================*/
        /*==============================================================================*/
        /* Constants                                                                    */
        /*==============================================================================*/

        /*==============================================================================*/
        /* Variables                                                                    */
        /*==============================================================================*/

    /*==================================================================================*/
    /*===================================            ===================================*/
    /*=================================== Operations ===================================*/
    /*===================================            ===================================*/
    /*==================================================================================*/

    /*==================================================================================*/
    /* Static initializer                                                               */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Constructors                                                                     */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Attribute Get Operations                                                         */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Attribute Set Operations                                                         */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Private Operations                                                               */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Protected Operations                                                             */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Package Operations                                                               */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Public Operations                                                                */
    /*==================================================================================*/

         /*=============================================================================*/
         /* OPERATION:   listQueues                                                     */
         /**
          * Lists all the queues.
          * <p>
          * @return The list of queues.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   @RequestMapping ("")
   public List<Queue> listQueues ()
   {
      return service.listQueues ();
   }

         /*=============================================================================*/
         /* OPERATION:   newQueue                                                       */
         /**
          * Creates a new queue entry.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   @RequestMapping ("/new")
   public Queue newQueue (
                           @RequestParam ("name")
                           String aName,
                           @RequestParam ("description")
                           String aDescription
                         )
   {
      return service.createNewQueue (aName, aDescription);
   }

         /*=============================================================================*/
         /* OPERATION:   subscribe                                                      */
         /**
          * Adds a given subscriber to the queue.
          * <p>
          * @param aQueueId
          * @param aSubscriberId
          * @return
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   @RequestMapping ("/{queueId}/subscribe")
   public Queue subscribe (
                           @PathVariable ("queueId")
                           String aQueueId,
                           @RequestParam ("subscriber")
                           String aSubscriberId
                          )
   {
      return service.subscribe (aQueueId, aSubscriberId);
   }

         /*=============================================================================*/
         /* OPERATION:   publish                                                        */
         /**
          * Publishes a message to the subscribers of a queue.
          * <p>
          * @param aQueueId
          *          The ID of the queue to publish to.
          *
          * @param aPublisherId
          *          The ID of the publisher sending the message.
          *
          * @param aPayloadParam
          *          (Optional) A payload passed in as a request parameter.
          *
          * @param aPayloadBody
          *          (Optional) A payload passed in as a request body.
          *
          * @return The IDs of the messages that were published.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   @RequestMapping ("/{queueId}/publish")
   public List<String> publish (
                                 @PathVariable ("queueId")
                                 String aQueueId,
                                 @RequestParam ("publisher")
                                 String aPublisherId,
                                 @RequestParam (value="payload", required=false)
                                 String aPayloadParam,
                                 @RequestBody (required=false)
                                 String aPayloadBody
                               )
   {
      if (aPayloadBody == null && aPayloadParam == null) {
         throw new IllegalArgumentException ("Payload must be specified either as a body or as a parameter called 'payload'.");
      }

      Object myPayload  = aPayloadBody;
      if (myPayload == null) {
         myPayload      = aPayloadParam;
      }

      return service.publish (aQueueId, aPublisherId, myPayload);
   }

         /*=============================================================================*/
         /* OPERATION:   next                                                           */
         /**
          * Gets the next message. An optional parameter (<code>previous</code>) can be
          * passed in which is the ID of the previous message which was handled. When
          * such a parameter is present, that message is first removed and then the next
          * one is gotten.
          * <p>
          * @param aQueueId
          * @param aSubscriberId
          * @return
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   @RequestMapping ("/{queueId}/next")
   public QueuedMessage next (
                              @PathVariable ("queueId")
                              String aQueueId,
                              @RequestParam ("subscriber")
                              String aSubscriberId,
                              @RequestParam (value="previous", required=false)
                              String aPreviousMessageId
                              )
   {
      QueuedMessage myNext = service.removeAndNext (aPreviousMessageId, aQueueId, aSubscriberId);
      if (myNext == null) {
         myNext = new QueuedMessage ();
      }
      return myNext;
   }


    /*==================================================================================*/
    /* Abstract Operations (definitions)                                                */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Abstract Operations (implementations)                                            */
    /*==================================================================================*/

    /*==================================================================================*/
    /* Class (static) Operations                                                        */
    /*==================================================================================*/
}

// EOF  ApiController.java
