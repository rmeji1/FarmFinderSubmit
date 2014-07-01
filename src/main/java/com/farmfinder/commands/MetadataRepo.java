package com.farmfinder.commands;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.farmfinder.metadata.Metadata;
import com.farmfinder.model.Category;

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

public interface MetadataRepo extends MongoRepository<Metadata, String> {
	public Metadata findByTitle(String title) ;
}