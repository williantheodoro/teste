package com.muvz.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.muvz.api.model.ClienteDto;
import com.muvz.domain.model.Cliente;

@Component
public class ClienteMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteDto toDto(Cliente cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}
	
	public List<ClienteDto> toListDto(List<Cliente> clientes) {
		return clientes.stream().map(this::toDto).collect(Collectors.toList());
	}
}
