package com.farmfinder.commands;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.farmfinder.model.Category;
import com.farmfinder.model.Payment;

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

public interface PaymentRepo extends MongoRepository<Payment, String> {

}
