package org.pelevin.web;

import org.pelevin.exceptions.EntityNotFoundException;
import org.pelevin.model.UserVO;
import org.pelevin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by dmitry on 27.08.15.
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService service;

	@Autowired
	public UserRestController(UserService service) {
		this.service = service;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	UserVO getUser(@PathVariable String userId) {
		return findById(userId);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<UserVO> getAll() {
		return this.service.findAll();
	}

	private UserVO findById(String userId) {
		return this.service.findById(userId).orElseThrow(
				() -> new EntityNotFoundException(UserVO.class, userId));
	}

}
