package com.muvz.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.muvz.domain.model.Cliente;
import com.muvz.domain.repository.ClienteRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ClienteControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	void deveCadastrarERetornar201() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
	}
	
	
	@Test
	void salva2ClientesRepetidosERetorna400() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
		
		resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void deveCadastrarEAtualizar() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);

		cliente.setEmail("testado2@gmail.com");
		
		resposta = testRestTemplate.exchange("/clientes/" + resposta.getBody().getId() , HttpMethod.PUT, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.OK);
		
		String emailAlterado = resposta.getBody().getEmail();
		
		assertEquals(emailAlterado, cliente.getEmail());
	}
	
	@Test
	void deveDeletarERetornar200() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
		
		cliente.setId(resposta.getBody().getId());
		
		ResponseEntity<Void> respostaDelete = testRestTemplate.exchange("/clientes/" + cliente.getId(), HttpMethod.DELETE, 
				request, Void.class);
		
		assertEquals(respostaDelete.getStatusCode(), HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	void deveRetornarListaDeClientes() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
		
		Cliente cliente2 = new Cliente();
		cliente2.setNome("Testado2");
		cliente2.setEmail("testado2@gmail.com");
		cliente2.setTelefone("11 11111-1111");
		cliente2.setCpf("111.111.111-11");
		HttpEntity<Cliente> request2 = new HttpEntity<Cliente>(cliente2);
		
		ResponseEntity<Cliente> resposta2 = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request2, Cliente.class);
		assertEquals(resposta2.getStatusCode(), HttpStatus.CREATED);
		
		
		ResponseEntity<Cliente[]> resposta3 = testRestTemplate.getForEntity("/clientes", Cliente[].class);
		assertEquals(resposta3.getStatusCode(), HttpStatus.OK);
		assertEquals(resposta3.getBody().length, 2);
		
		
	}
	
	@Test
	void deveRetornarClientePorId() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(cliente);
		
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, 
				request, Cliente.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
		
		
		ResponseEntity<Cliente> resposta2 = testRestTemplate
					.getForEntity("/clientes/" + resposta.getBody().getId() , Cliente.class);
		assertEquals(resposta2.getStatusCode(), HttpStatus.OK);
		assertEquals(cliente.getEmail(), resposta2.getBody().getEmail());
		
	}
	
	
	
	@AfterEach
	void end() {
		clienteRepository.deleteAll();
	}
}
