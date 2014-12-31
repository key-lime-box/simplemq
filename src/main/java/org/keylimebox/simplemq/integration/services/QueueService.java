
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.integration.services;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.keylimebox.simplemq.core.model.Queue;
import org.keylimebox.simplemq.core.model.QueuedMessage;
import org.keylimebox.simplemq.core.repositories.QueueRepository;
import org.keylimebox.simplemq.core.repositories.QueuedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       QueueService                                                            */
/**
 * Provides various services to interact with the queues and subscribers.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
@Service
public class QueueService
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
                /* ATTRIBUTE: queueRepo                                                 */
                /**
                 * Data access object for queues.
                 */
                /*======================================================================*/
   @Autowired
   private QueueRepository                queueRepo;

                /*======================================================================*/
                /* ATTRIBUTE: messageRepo                                               */
                /**
                 * Data access object for messages.
                 */
                /*======================================================================*/
   @Autowired
   private QueuedMessageRepository        messageRepo;

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
          * Returns a list of all the queues.
          * <p>
          * @return The list of queues.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public List<Queue> listQueues ()
   {
      return queueRepo.findAll ();
   }

         /*=============================================================================*/
         /* OPERATION:   createNew                                                      */
         /**
          * Creates and save a new queue instance.
          * <p>
          * @param aQueueName
          *          The name for the queue.
          *
          * @param aQueueDescription
          *          The description for the queue.
          *
          * @return the saved instance.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public Queue createNewQueue (String aQueueName, String aQueueDescription)
   {
      Queue    myQueue        = new Queue ();
      myQueue.setName         (aQueueName);
      myQueue.setDescription  (aQueueDescription);

      return queueRepo.save (myQueue);
   }

         /*=============================================================================*/
         /* OPERATION:   subscribe                                                      */
         /**
          * Registers a subscriber with the queue.
          * <p>
          * @param aQueueId
          *          The ID of the queue to subscribe to.
          *
          * @param aSubscriberId
          *          The ID of the subscriber that wish to subscribe to the queue.
          *
          * @return The queue modified.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public Queue subscribe (String aQueueId, String aSubscriberId)
   {
      Queue myQueue = queueRepo.findOne (aQueueId);
      myQueue.addSubscriber (aSubscriberId);

      return queueRepo.save (myQueue);
   }

         /*=============================================================================*/
         /* OPERATION:   publish                                                        */
         /**
          * Publishes the payload to all subscribers.
          * <p>
          * @param aQueueId
          * @param aPublisherId
          * @param aPayload
          * @return The IDs of the queued messages.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public List<String> publish (String aQueueId, String aPublisherId, Object aPayload)
   {
      Date           myNow          = new Date ();
      List<String>   mySubscribers  = queueRepo.findOne (aQueueId).getSubscribers ();
      List<String>   myMessageIds   = new ArrayList<String> ();

      for (String mySubscriber : mySubscribers) {
         QueuedMessage myMessage    = new QueuedMessage ();
         myMessage.setDateQueued    (myNow);
         myMessage.setPayload       (aPayload);
         myMessage.setPublisherId   (aPublisherId);
         myMessage.setSubscriberId  (mySubscriber);

         myMessageIds.add (messageRepo.save (myMessage).getId ());
      }

      return myMessageIds;
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

// EOF  QueueService.java
