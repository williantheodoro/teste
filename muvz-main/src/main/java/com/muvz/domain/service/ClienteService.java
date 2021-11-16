package com.muvz.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.muvz.domain.exception.NegocioException;
import com.muvz.domain.model.Cliente;
import com.muvz.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {

	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		
		boolean isEmailEmUso = clienteRepository.findByEmail(cliente.getEmail()).stream()
				.anyMatch(cli -> (cliente.getId() == null && cli.getEmail().equalsIgnoreCase(cliente.getEmail()) ||
				(cliente.getId() != null && !cli.getId().equals(cliente.getId()) && cli.getEmail().equalsIgnoreCase(cliente.getEmail()))));
		
		boolean isTelefoneEmUso = clienteRepository.findByTelefone(cliente.getTelefone()).stream()
				.anyMatch(cli -> (cliente.getId() == null && cli.getTelefone().equals(cliente.getTelefone())) ||
						(cliente.getId() != null && !cli.getId().equals(cliente.getId()) && cli.getTelefone().equals(cliente.getTelefone())));
		
		
		boolean isCpfEmUso = clienteRepository.findByCpf(cliente.getCpf()).stream()
				.anyMatch(cli -> (cliente.getId() == null && cli.getCpf().equals(cliente.getCpf())) ||
						(cliente.getId() != null && !cli.getId().equals(cliente.getId()) && cli.getCpf().equals(cliente.getCpf())));;
		
		if(isEmailEmUso || isTelefoneEmUso || isCpfEmUso) {
			String atributo = isCpfEmUso ? "cpf" : isTelefoneEmUso ? "telefone" : "e-mail";
			throw new NegocioException(String.format("O %s já está cadastrado", atributo));
		}
		
		return clienteRepository.save(cliente);
	}
	
}
