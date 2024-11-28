package com.devsuperior.dscommerce.services.exceptions;

//RuntimeException - nao exige o try/catch, diferente do Exception que exige.

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
