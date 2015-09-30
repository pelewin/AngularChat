package org.pelevin.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dmitry on 27.08.15.
 */
@Document(collection = "users")
public class UserVO extends BaseVO {

	private String name;

	private String password;

	public UserVO() {
		super();
		// for JPA
	}

	public UserVO(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
