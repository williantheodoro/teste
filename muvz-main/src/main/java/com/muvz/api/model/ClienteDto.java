package com.muvz.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

    private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;

	private String email;
}
