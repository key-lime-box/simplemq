
/*======================================================================================*/
/*                                  Package Definition                                  */
/*======================================================================================*/

package org.keylimebox.simplemq.core.repositories;

/*======================================================================================*/
/*                                       Imports                                        */
/*======================================================================================*/

import org.keylimebox.simplemq.core.model.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;

/*======================================================================================*/
/*                           Class Definition / Implementation                          */
/*======================================================================================*/
/*======================================================================================*/
/* CLASS:       PublisherRepository                                                     */
/**
 * Data access for publishers.
 * <p>
 * @author      etlweather
 * @since       Dec 31, 2014
 */
/*======================================================================================*/
@SuppressWarnings ("nls")
public interface PublisherRepository extends MongoRepository<Publisher, String>
{


}

// EOF  PublisherRepository.java
