package com.muvz.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muvz.domain.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByTelefone(String telefone);
}
