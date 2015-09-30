package org.pelevin.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by dmitry on 27.08.15.
 */
@Document(collection = "messages")
public class MessageVO extends BaseVO {

	private UserVO user;
	private Date date;
	private String text;

	public MessageVO() {
		super();
		// for JPA
	}

	public MessageVO(UserVO user, String text) {
		this.text = text;
		this.date = new Date();
		this.user = user;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
