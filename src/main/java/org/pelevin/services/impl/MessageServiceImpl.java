package org.pelevin.services.impl;

import org.pelevin.model.MessageVO;
import org.pelevin.model.UserVO;
import org.pelevin.repositories.MessageRepository;
import org.pelevin.repositories.UserRepository;
import org.pelevin.services.MessageService;
import org.pelevin.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 23.09.15.
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageVO, MessageRepository> implements MessageService {

	@Override
	public Optional<MessageVO> findById(String id) {
		return getRepository().findById(id);
	}
}
