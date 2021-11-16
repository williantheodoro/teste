package com.muvz.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.muvz.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String msg = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!";
		return super.handleExceptionInternal(ex, criarProblema(ex, status.value(), msg), headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return super.handleExceptionInternal(ex, criarProblema(ex, status.value(), ex.getMessage()),
				new HttpHeaders(), status, request);
	}
	
	
	private Problema criarProblema(Exception ex, int status, String msg) {
		Problema problema = new Problema();
		problema.setStatus(status);
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(msg);
		if(ex instanceof BindException) {
			problema.setCampos(((BindException) ex).getAllErrors()
					.stream()
					.map(erro -> new Problema.Campo(((FieldError) erro).getField(), messageSource.getMessage(erro,
							LocaleContextHolder.getLocale())))
					.collect(Collectors.toList()));
		}
		return problema;
	}
}
