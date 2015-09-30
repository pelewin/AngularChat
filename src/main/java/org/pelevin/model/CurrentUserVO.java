package org.pelevin.model;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by dmitry on 30.09.15.
 */
public class CurrentUserVO extends User {

	private UserVO user;

	public CurrentUserVO(UserVO user) {
		super(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList("USER"));
		this.user = user;
		this.user.setPassword("");
	}

	public UserVO getUser() {
		return user;
	}

	public String getId() {
		return user.getId();
	}

}
