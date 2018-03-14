package com.tikal.cacao.model;

import javax.crypto.SecretKey;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Llave {

	@Id
	private Long id;
	
	private SecretKey value;
//	private String value;
	
	public Long getId() {
		return id;
	}

//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
	
	

	public SecretKey getValue() {
		return value;
	}

	public void setValue(SecretKey value) {
		this.value = value;
	}

}
