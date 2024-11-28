package com.devsuperior.dscommerce.dto;

import java.time.Instant;

//Criado essa classe a partir dos dados do postman que aparece o error, os dados sao path, timestamp, status e error.
//CustomIZAR o error que aparece, é isto basicamente.
public class CustomError {

	private Instant timestamp;
	private Integer status;
	private String error;
	private String path;

//nao se coloca o construtor vazio aqui, pois eu quero ao passar o CustomError com os paramentros
	public CustomError(Instant timestamp, Integer status, String error, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getPath() {
		return path;
	}
}
