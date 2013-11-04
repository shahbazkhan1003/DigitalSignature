package com.shahbaz.digital.digitalSignature;

import org.springframework.core.io.Resource;

/**
 * 
 * @author shabby
 *
 */
public class SignatureRequirements {
	
	private String message;
	

	
	private String password;
	
	private String alias;

	private Resource keyStoreFile;


	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Resource getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(Resource keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
