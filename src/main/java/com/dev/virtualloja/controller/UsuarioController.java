package com.dev.virtualloja.controller;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.virtual.loja.domain.Usuario;
import com.virtual.loja.exception.BadResourceException;
import com.virtual.loja.exception.ResourceAlreadyExistsException;
import com.virtual.loja.exception.ResourceNotFoundException;
import com.virtual.loja.service.ServicoUsuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="Usuário", description="Controle Usuário")
public class ControleUsuario {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServicoUsuario servicoUsuario;

	@GetMapping(value = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Usuario>> findAll(
			@Parameter(description="Nome para pesquisa...",allowEmptyValue=true)
			@RequestBody(required = false) String nome, 
			@Parameter(description ="Paginação",example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			Pageable pageable) {
		if (StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(servicoUsuario.findAll(pageable));
		} else {
			return ResponseEntity.ok(servicoUsuario.findAllByNome(nome, pageable));
		}
	}
	
	@Operation(summary="Buscar ID", description="Buscar usuário pelo ID", tags={"Usuário"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário encontrado!"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado!")})
	@GetMapping(value = "/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> findUsuarioById(@PathVariable long id) {
		try {
			Usuario usuario = servicoUsuario.findById(id);
			return ResponseEntity.ok(usuario);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

	@PostMapping(value = "/usuario")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
		try {
			Usuario novoUsuario = servicoUsuario.save(usuario);
			return ResponseEntity.created(new URI("/api/usuario/" + novoUsuario.getId())).body(usuario);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/usuario/{id}")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario, @PathVariable long id) {
		try {
			usuario.setId(id);
			servicoUsuario.update(usuario);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping(path = "/usuario/{id}")
	public ResponseEntity<Void> deleteUsuarioById(@PathVariable long id) {
		try {
			servicoUsuario.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}