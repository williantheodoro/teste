package com.muvz.domain.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.muvz.domain.model.Cliente;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@BeforeAll
	void start() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		if(clienteRepository.findByEmail(cliente.getEmail()).isEmpty())
			clienteRepository.save(cliente);
	}
	
	@Test
	void buscarClientePorEmailTest() {
		Optional<Cliente> cliente = clienteRepository.findByEmail("testado@gmail.com");
		assertTrue(cliente.isPresent());
		
		cliente = clienteRepository.findByEmail("teste");
		assertTrue(cliente.isEmpty());
	}
	
	@Test
	void buscarClientePorCpfTest() {
		Optional<Cliente> cliente = clienteRepository.findByCpf("000.000.000-00");
		assertTrue(cliente.isPresent());
		
		cliente = clienteRepository.findByCpf("000");
		assertTrue(cliente.isEmpty());
	}
	
	@Test
	void buscarClientePorTelefoneTest() {
		Optional<Cliente> cliente = clienteRepository.findByTelefone("00 00000-0000");
		assertTrue(cliente.isPresent());
		
		cliente = clienteRepository.findByTelefone("000");
		assertTrue(cliente.isEmpty());
	}
	
	@AfterAll
	void end() {
		clienteRepository.deleteAll();
	}
}
