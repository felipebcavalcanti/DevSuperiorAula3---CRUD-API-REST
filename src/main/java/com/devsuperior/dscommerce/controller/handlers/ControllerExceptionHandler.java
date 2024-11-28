package com.devsuperior.dscommerce.controller.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidationError;
import com.devsuperior.dscommerce.services.exceptions.DataBaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class) // A Annotation serve para capturar a excessao que vc quer, no
														// caso é essa que esta como parametro.
	// Tem que retornar uma ResponseEntity pq tem que ser no formato do objeto | O
	// CustomError é o tipo que a gente vai retornar
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {//Parametros que vai é o error e a rota
		HttpStatus status = HttpStatus.NOT_FOUND;// pega o tipo de HttpStatus, que aqui seria no caso o NOT_FOUND
		// Cria um obj ''err'' do tipo CustomError e ai, cria um nova instancia agora
		// passando os parametros que estao dentro do CustomError
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		// Retorna o tipo da funcao como ResponseEntity passando o status e no corpo da
		// resposta para o cliente coloca o ''err'' que foi customizado.s
		return ResponseEntity.status(404).body(err);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<CustomError> databaseNotFound(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(404).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(Instant.now(), status.value(), "Invalid data",
				request.getRequestURI());

		for (FieldError f : e.getBindingResult()
				.getFieldErrors()) { /*
										 * Tratamento dado para customizar e nos mostrar os errors dos campos e
										 * customizar o error que aparecer super grande no postman
										 */
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
}
//o Controller Advice serve para capturar GLOBALMENTE os erros e trata-los
//Annotation para capturar o error e trata-lo
//Tem que fazer as funcoes para cada excecoes