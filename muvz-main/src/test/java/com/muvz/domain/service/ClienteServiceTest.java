package com.muvz.domain.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.muvz.domain.exception.NegocioException;
import com.muvz.domain.model.Cliente;
import com.muvz.domain.repository.ClienteRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@Test
	public void testarMetodoSalvarAoCadastrarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		
		Mockito.when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.empty());
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
		Mockito.when(clienteRepository.findByTelefone(cliente.getTelefone())).thenReturn(Optional.empty());
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		Cliente clienteSalvo = clienteService.salvar(cliente);
		
		assertEquals(cliente, clienteSalvo);
		
	}
	
	@Test
	public void testarMetodoSalvarAoAtualizarCliente() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		
		Mockito.when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByTelefone(cliente.getTelefone())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		Cliente clienteSalvo = clienteService.salvar(cliente);
		
		assertEquals(cliente, clienteSalvo);
		
	}
	
	
	@Test
	public void testarMetodoSalvarEmailDuplicado() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Testado2");
		cliente2.setEmail("testado@gmail.com");
		cliente2.setTelefone("22 22222-2222");
		cliente2.setCpf("222.222.222-22");
		
		Mockito.when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente2));
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByTelefone(cliente.getTelefone())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		assertThatExceptionOfType(NegocioException.class).isThrownBy(() -> clienteService.salvar(cliente));
		
	}
	
	@Test
	public void testarMetodoSalvarTelefoneDuplicado() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Testado2");
		cliente2.setEmail("testado2@gmail.com");
		cliente2.setTelefone("00 00000-0000");
		cliente2.setCpf("222.222.222-22");
		
		Mockito.when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByTelefone(cliente.getTelefone())).thenReturn(Optional.of(cliente2));
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		assertThatExceptionOfType(NegocioException.class).isThrownBy(() -> clienteService.salvar(cliente));
		
	}
	
	@Test
	public void testarMetodoSalvarCpfDuplicado() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Testado");
		cliente.setEmail("testado@gmail.com");
		cliente.setTelefone("00 00000-0000");
		cliente.setCpf("000.000.000-00");
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Testado2");
		cliente2.setEmail("testado2@gmail.com");
		cliente2.setTelefone("22 22222-2222");
		cliente2.setCpf("000.000.000-00");
		
		Mockito.when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente2));
		Mockito.when(clienteRepository.findByTelefone(cliente.getTelefone())).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		assertThatExceptionOfType(NegocioException.class).isThrownBy(() -> clienteService.salvar(cliente));
		
	}
	
	
}
