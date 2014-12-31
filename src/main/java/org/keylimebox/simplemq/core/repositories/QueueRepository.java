
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.core.repositories;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import org.keylimebox.simplemq.core.model.Queue;
import org.springframework.data.mongodb.repository.MongoRepository;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       QueueRepository                                                         */
/**
 * Data access for queue definitions.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
public interface QueueRepository extends MongoRepository<Queue, String>
{

}

// EOF  QueueRepository.java
