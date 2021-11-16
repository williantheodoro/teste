package com.muvz.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.muvz.api.mapper.ClienteMapper;
import com.muvz.api.model.ClienteDto;
import com.muvz.domain.model.Cliente;
import com.muvz.domain.repository.ClienteRepository;
import com.muvz.domain.service.ClienteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {

	private ClienteRepository clienteRepository;
	
	private ClienteService clienteService;
	
	private ClienteMapper clienteMapper;
	
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ClienteDto>> buscarClientes() {
		
		List<ClienteDto> clientes =  clienteMapper.toListDto(clienteRepository.findAll());
		
		return ResponseEntity.ok(clientes);
		
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ClienteDto> buscarCliente(@PathVariable Long id) {
		
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		ClienteDto cliente = clienteMapper.toDto(clienteOptional.get());
		
		return ResponseEntity.ok(cliente);
		
	}
	
	@PostMapping(produces = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteDto cadastrarCliente(@RequestBody @Valid Cliente cliente) {
		return clienteMapper.toDto(clienteService.salvar(cliente));
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id,
			@RequestBody @Valid Cliente cliente) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(id);
		
		Cliente clienteAtualizado = clienteService.salvar(cliente);
		
		ClienteDto clienteDto = clienteMapper.toDto(clienteAtualizado);
		
		return ResponseEntity.ok(clienteDto);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
		
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
