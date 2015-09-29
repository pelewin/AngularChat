package org.pelevin.repositories;

import org.pelevin.model.CustomerVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
public interface CustomerRepository extends MongoRepository<CustomerVO, String> {


	Optional<CustomerVO> findById(String id);
	Optional<CustomerVO> findByName(String name);

}
