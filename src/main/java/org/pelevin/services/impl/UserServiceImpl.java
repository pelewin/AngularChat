package org.pelevin.services.impl;

import org.pelevin.model.UserVO;
import org.pelevin.repositories.UserRepository;
import org.pelevin.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 23.09.15.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserVO, UserRepository> implements UserService {

	@Override
	public Optional<UserVO> findById(String id) {
		return getRepository().findById(id);
	}
}
