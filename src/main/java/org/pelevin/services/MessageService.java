package org.pelevin.services;

import org.pelevin.model.MessageVO;
import org.pelevin.model.UserVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
@Service
public interface MessageService extends BaseService<MessageVO>{

	Optional<MessageVO> findById(String id);

}
