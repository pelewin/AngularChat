package org.pelevin.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by dmitry on 29.08.15.
 */
abstract public class BaseVO {

	@Id
	private String id;
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public BaseVO() {
		//for JPA
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
