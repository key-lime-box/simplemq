
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.core.repositories;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import org.keylimebox.simplemq.core.model.QueuedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       QueuedMessageRepository                                                 */
/**
 * Data access for QueuedMessage.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
public interface QueuedMessageRepository extends MongoRepository<QueuedMessage, String>
{


}

// EOF  PublisherRepository.java
