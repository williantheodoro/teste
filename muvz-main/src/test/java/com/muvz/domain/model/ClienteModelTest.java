package com.muvz.domain.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClienteModelTest {

	@Autowired
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private Cliente cliente;

	@BeforeEach
	void start() {
		cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
	}

	@Test
	void testClienteAtributosEmBranco() {
		
		Set<ConstraintViolation<Cliente>> violacao = validator.validate(cliente, Default.class);
		assertTrue(violacao.isEmpty());
		
		cliente.setNome("");
		cliente.setTelefone("");
		cliente.setCpf("");
		violacao = validator.validate(cliente, Default.class);
		assertFalse(violacao.isEmpty());
		
	}


	@Test
	void testClienteEmailPadraoInvalido() {
		Set<ConstraintViolation<Cliente>> violacao = validator.validate(cliente);
		assertTrue(violacao.isEmpty());

		cliente.setEmail("Testado");
		violacao = validator.validate(cliente);
		assertFalse(violacao.isEmpty());

	}
	
	
	@Test
	void testValidaAtributosNull() {
		cliente.setId(null);
		Set<ConstraintViolation<Cliente>> violacao = validator
				.validate(cliente);
		assertTrue(violacao.isEmpty());
		
		cliente.setId(1L);
		violacao = validator.validate(cliente);
		assertFalse(violacao.isEmpty());
		
	}

}
