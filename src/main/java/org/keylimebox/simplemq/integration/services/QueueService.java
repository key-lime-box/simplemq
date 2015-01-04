
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
import org.keylimebox.simplemq.integration.model.QueueStatus;
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

                /*======================================================================*/
                /* ATTRIBUTE: subscriberService                                         */
                /**
                 *
                 */
                /*======================================================================*/
   @Autowired
   private SubscriberService              subscriberService;

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
         /* OPERATION:   status                                                         */
         /**
          * Reports the status of a given queue.
          * <p>
          * @param aQueueId
          * @return
          * <p>
          * @since Jan 3, 2015
          */
         /*=============================================================================*/
   public QueueStatus status (String aQueueId)
   {
      QueueStatus    myStatus             = new QueueStatus ();
      Queue          myQueue              = queueRepo.findOne (aQueueId);
      Long           myNbrItems           = messageRepo.countByQueueId (aQueueId);
      QueuedMessage  myOldest             = messageRepo.findFirstByQueueIdOrderByDateQueuedAsc (aQueueId);


      myStatus.setQueueDescription        (myQueue.getDescription ());
      myStatus.setQueueId                 (myQueue.getId ());
      myStatus.setQueueName               (myQueue.getName ());
      myStatus.setNbrMessage              (myNbrItems);
      myStatus.setNbrSubscribers          (myQueue.getSubscribers ().size ());

      if (myOldest != null) {
         myStatus.setOldestMessage        (myOldest.getDateQueued ());
         myStatus.setOldestSubscriberId   (myOldest.getSubscriberId ());
         myStatus.setOldestSubscriber     (subscriberService.get (myOldest.getSubscriberId ()).getName ());
      }

      return myStatus;
   }

         /*=============================================================================*/
         /* OPERATION:   status                                                         */
         /**
          * Reports the status of a given queue.
          * <p>
          * @param aQueueId
          * @return
          * <p>
          * @since Jan 3, 2015
          */
         /*=============================================================================*/
   public QueueStatus status (String aQueueId, String aSubscriberId)
   {
      QueueStatus    myStatus             = new QueueStatus ();
      Queue          myQueue              = queueRepo.findOne (aQueueId);
      Long           myNbrItems           = messageRepo.countByQueueIdAndSubscriberId (aQueueId, aSubscriberId);
      QueuedMessage  myOldest             = messageRepo.findFirstByQueueIdAndSubscriberIdOrderByDateQueuedAsc (aQueueId, aSubscriberId);


      myStatus.setQueueDescription        (myQueue.getDescription ());
      myStatus.setQueueId                 (myQueue.getId ());
      myStatus.setQueueName               (myQueue.getName ());
      myStatus.setNbrMessage              (myNbrItems);
      myStatus.setNbrSubscribers          (myQueue.getSubscribers ().size ());

      if (myOldest != null) {
         myStatus.setOldestMessage        (myOldest.getDateQueued ());
         myStatus.setOldestSubscriberId   (myOldest.getSubscriberId ());
         myStatus.setOldestSubscriber     (subscriberService.get (myOldest.getSubscriberId ()).getName ());
      }

      return myStatus;
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
      Queue          myQueue        = queueRepo.findOne (aQueueId);
      if (myQueue == null) {
         throw new IllegalArgumentException (aQueueId + " is not a valid Queue ID");
      }
      List<String>   mySubscribers  = myQueue.getSubscribers ();
      List<String>   myMessageIds   = new ArrayList<String> ();

      for (String mySubscriber : mySubscribers) {
         QueuedMessage myMessage    = new QueuedMessage ();
         myMessage.setDateQueued    (myNow);
         myMessage.setPayload       (aPayload);
         myMessage.setPublisherId   (aPublisherId);
         myMessage.setQueueId       (aQueueId);
         myMessage.setSubscriberId  (mySubscriber);

         myMessageIds.add (messageRepo.save (myMessage).getId ());
      }

      return myMessageIds;
   }

         /*=============================================================================*/
         /* OPERATION:   next                                                           */
         /**
          * Returns the next element from the queue for the given subscriber.
          * <p>
          * @param aQueueId
          * @param aSubscriberId
          * @return
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public QueuedMessage next (String aQueueId, String aSubscriberId)
   {
      return messageRepo.findFirstByQueueIdAndSubscriberIdOrderByDateQueuedAsc (aQueueId, aSubscriberId);
   }

         /*=============================================================================*/
         /* OPERATION:   removeAndNext                                                  */
         /**
          * Removes the message with the given ID and returns the next message.
          * <p>
          * @param aMessageIdToRemove
          *          The ID of the message to remove.
          *
          * @param aQueueId
          *          The ID of the queue to get the next message from.
          *
          * @param aSubscriberId
          *          The ID of the subscriber to get the next message for.
          *
          * @return The next message.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public QueuedMessage removeAndNext (
                                          String aMessageIdToRemove,
                                          String aQueueId,
                                          String aSubscriberId
                                      )
    {
      if (aMessageIdToRemove != null) {
         messageRepo.delete (aMessageIdToRemove);
      }
      return next (aQueueId, aSubscriberId);
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
