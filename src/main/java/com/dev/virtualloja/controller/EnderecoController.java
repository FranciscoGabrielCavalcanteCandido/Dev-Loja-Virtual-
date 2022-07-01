package com.dev.virtualloja.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.virtualloja.domain.Endereco;
import com.dev.virtualloja.service.EnderecoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.Data;

@RestController
@Data
@RequestMapping("/api")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	
	@Operation(summary = "Busca endereco", description = "Buscar produto por ID", tags = {"produto"})
	@GetMapping(value = "/endereco/{cep}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Endereco> findEnderecoByCep(@PathVariable String cep){
	
			Endereco endereco = enderecoService.getEndereco(cep);
			return ResponseEntity.ok(endereco);
		}


	
}
