package com.farmfinder.commands;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.farmfinder.model.Farm;

/* Uses MongoRepository
 * Avail Methods by default: 
 * count() ;
 * delete(ID id)
 * delete (T entity) ;
 * deleteAll() ;
 * exists(ID id) ;
 * findAll() ;
 * findAll(Iterable<ID> ids) ;
 * findOne(ID id) ;
 * save(Iterable<S> entities) ;
 * save(S entity) ;
*/

public interface FarmRepo extends MongoRepository<Farm, String> {
	List<Farm> findByState( String state ) ;
	Farm findByEmail( String email ) ;
}
