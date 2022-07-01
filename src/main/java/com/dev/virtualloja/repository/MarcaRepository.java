package com.dev.virtualloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtual.loja.domain.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{
	
	@Query(value ="select a from Marca a where a.nome like %?1% ")
	Page<Marca> findByNome(String nome, Pageable page);
	
	Page<Marca> findAll(Pageable page);

}
