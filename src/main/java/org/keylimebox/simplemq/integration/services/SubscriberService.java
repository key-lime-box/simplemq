
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.integration.services;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import java.util.List;

import org.keylimebox.simplemq.core.model.Subscriber;
import org.keylimebox.simplemq.core.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       SubscriberService                                                       */
/**
 * Provides various services to interact with the subscribers.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
@Service
public class SubscriberService
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
                /* ATTRIBUTE: repository                                                */
                /**
                 * Data access object.
                 */
                /*======================================================================*/
   @Autowired
   private SubscriberRepository        repository;

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
         /* OPERATION:   list                                                           */
         /**
          * Returns a list of all the subscribers.
          * <p>
          * @return The list of subscribers.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public List<Subscriber> list ()
   {
      return repository.findAll ();
   }

         /*=============================================================================*/
         /* OPERATION:   createNew                                                      */
         /**
          * Creates and save a new Subscriber instance.
          * <p>
          * @param aName
          *          The name for the Subscriber.
          *
          * @return the saved instance.
          * <p>
          * @since Dec 31, 2014
          */
         /*=============================================================================*/
   public Subscriber createNew (String aName)
   {
      Subscriber myEntity   = new Subscriber ();

      myEntity.setName     (aName);

      return repository.save (myEntity);
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