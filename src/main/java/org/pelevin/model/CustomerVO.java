package org.pelevin.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dmitry on 27.08.15.
 */
@Document(collection = "customers")
public class CustomerVO extends BaseVO {

	private String name;
	private String address;
	private String telephoneNumber;

	public CustomerVO() {
		super();
		// for JPA
	}

	public CustomerVO(String name, String address, String telephoneNumber) {
		this.name = name;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
}
