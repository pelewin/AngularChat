package org.pelevin.repositories;

import org.pelevin.model.CustomerVO;
import org.pelevin.model.MessageVO;
import org.pelevin.model.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
public interface MessageRepository extends MongoRepository<MessageVO, String> {


	Optional<MessageVO> findById(String id);

}
