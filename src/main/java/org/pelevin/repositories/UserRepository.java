package org.pelevin.repositories;

import org.pelevin.model.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
public interface UserRepository extends MongoRepository<UserVO, String> {


	Optional<UserVO> findById(String id);
	Optional<UserVO> findByName(String name);

}
