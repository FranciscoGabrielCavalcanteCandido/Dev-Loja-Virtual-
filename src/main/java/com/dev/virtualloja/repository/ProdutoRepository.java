package com.dev.virtualloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.virtualloja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(value ="select a from Produto a where a.nome like %?1% ")
	Page<Produto> findByNome(String nome, Pageable page);
	
	Page<Produto> findAll(Pageable page);

}
