
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.core.repositories;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import org.keylimebox.simplemq.core.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       SubscriberRepository                                                    */
/**
 * Data access for subscribers.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
public interface SubscriberRepository extends MongoRepository<Subscriber, String>
{


}

// EOF  PublisherRepository.java
