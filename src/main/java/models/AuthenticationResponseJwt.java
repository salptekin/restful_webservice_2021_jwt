package models;

import java.io.Serializable;

public class AuthenticationResponseJwt implements Serializable{
	
	private final String jwt;
	
	public AuthenticationResponseJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
