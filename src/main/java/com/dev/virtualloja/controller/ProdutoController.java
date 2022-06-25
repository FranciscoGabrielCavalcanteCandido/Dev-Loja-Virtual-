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

import com.virtual.loja.domain.Produto;
import com.virtual.loja.exception.BadResourceException;
import com.virtual.loja.exception.ResourceAlreadyExistsException;
import com.virtual.loja.exception.ResourceNotFoundException;
import com.virtual.loja.service.ServicoProduto;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Produto", description = "Controle Produto")
public class ProdutoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServicoProduto ServicoProduto;

	@GetMapping(value = "/produto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Produto>> findAll(@RequestBody(required = false) String nome, Pageable pageable) {

		if (StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(ServicoProduto.findAll(pageable));
		} else {
			return ResponseEntity.ok(ServicoProduto.findAllByNome(nome, pageable));
		}
	}

	@GetMapping(value = "/produto/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produto> findProdutoById(@PathVariable long id) {
		try {
			Produto Produto = ServicoProduto.findById(id);
			return ResponseEntity.ok(Produto);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

	@PostMapping(value = "/produto")
	public ResponseEntity<Produto> addProduto(@RequestBody Produto Produto) throws URISyntaxException {
		try {
			Produto novoProduto = ServicoProduto.save(Produto);
			return ResponseEntity.created(new URI("/api/Produto/" + novoProduto.getCodigo())).body(Produto);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/produto/{codigo}")
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto Produto, @PathVariable long codigo) {
		try {
			Produto.setCodigo(codigo);
			ServicoProduto.update(Produto);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping(path = "/produto/{codigo}")
	public ResponseEntity<Void> deleteProdutoById(@PathVariable long codigo) {
		try {
			ServicoProduto.deleteById(codigo);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}
