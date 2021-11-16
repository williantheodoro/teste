package com.muvz.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cli_cliente")
public class Cliente {

	@EqualsAndHashCode.Include
	@Null(message = "Id deve ser nulo")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "Cpf não pode ser vazio")
	@Column(nullable = false, unique = true)
	private String cpf;
	
	@NotBlank(message = "Telefone não pode ser vazio")
	@Column(nullable = false, unique = true)
	private String telefone;
	
	@Email(message = "Padrão de e-mail inválido")
	@Column(nullable = false, unique = true)
	private String email;
}
