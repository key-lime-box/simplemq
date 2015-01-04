
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

   public QueuedMessage findFirstByQueueIdAndSubscriberIdOrderByDateQueuedAsc (String aQueueId, String aSubscriberId);

   public QueuedMessage findFirstByQueueIdOrderByDateQueuedAsc (String aQueueId);

   public Long countByQueueId (String aQueueId);

   public Long countByQueueIdAndSubscriberId (String aQueueId, String aSubscriberId);


}

// EOF  PublisherRepository.java
