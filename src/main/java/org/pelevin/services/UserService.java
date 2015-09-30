package org.pelevin.services;

import org.pelevin.model.UserVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
@Service
public interface UserService extends BaseService<UserVO> {

	Optional<UserVO> findById(String id);
	Optional<UserVO> findByName(String name);

}
