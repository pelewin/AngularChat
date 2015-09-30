package org.pelevin.services.impl;

import org.pelevin.model.CurrentUserVO;
import org.pelevin.model.UserVO;
import org.pelevin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by dmitry on 30.09.15.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public CurrentUserVO loadUserByUsername(String name) throws UsernameNotFoundException {
		UserVO user = userService.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with name=%s was not found", name)));

		CurrentUserVO userDetails = new CurrentUserVO(user);
		return userDetails;
	}

}
