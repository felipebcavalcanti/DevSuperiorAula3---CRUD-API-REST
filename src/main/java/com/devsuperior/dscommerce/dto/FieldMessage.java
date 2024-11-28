package com.devsuperior.dscommerce.dto;

//Classe feita para alterar as mensagens feitas pelo SpringBoot quando da error.. Mensagens customizadas dos campos dos erros.
public class FieldMessage {
	private String fieldName;
	private String message;

	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}

}
