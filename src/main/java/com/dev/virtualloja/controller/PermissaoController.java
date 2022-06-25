package com.dev.virtualloja.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.models.media.MediaType;



public class PermissaoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ServicoPermissao ServicoPermissao{}
	
	@GetMapping(value="/permissao", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Permissao>> findAll(@RequestBody(required=false)String nome, Pageable pageable)
	
			
}
