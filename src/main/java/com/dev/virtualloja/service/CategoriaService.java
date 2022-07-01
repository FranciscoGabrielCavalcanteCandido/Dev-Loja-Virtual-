package com.dev.virtualloja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.virtualloja.exception.BadResourceException;
import com.dev.virtualloja.exception.ResourceAlreadyExistsException;
import com.dev.virtualloja.exception.ResourceNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaService repositorioCategoria;
	
	private boolean existsById(Long id) {
		return repositorioCategoria.existsById(id);
	}
	
	public Categoria findById(Long id) throws ResourceNotFoundException{
		Categoria Categoria = repositorioCategoria.findById(id).orElse(null);
		if(Categoria==null) {
			throw new ResourceNotFoundException("Categoria não encontrado com o id: "+id);
		}else return Categoria;
	}
	
	public Page<Categoria> findAll(Pageable pageable){
		return repositorioCategoria.findAll(pageable);
	}
	
	public Page<Categoria> findAllByNome(String nome, Pageable page){
		Page<Categoria>Categorias = repositorioCategoria.findByNome(nome,page);
		return Categorias;
	}
	
	public Categoria save(Categoria Categoria) throws BadResourceException,ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(Categoria.getNome())) {
			if(Categoria.getId() != null && existsById(Categoria.getId())) {
				throw new ResourceAlreadyExistsException("Categoria com id: "+ Categoria.getId()+"já existe.");
			}
			return repositorioCategoria.save(Categoria);
		}
		else {
			BadResourceException exc =  new BadResourceException("Erro ao salvar usuário");
			exc.addErrorMessages("Usuário está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Categoria Categoria) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(Categoria.getNome())) {
			if(!existsById(Categoria.getId())) {
				throw new ResourceNotFoundException("Categoria não encontrado com o id: "+Categoria.getId());
			}
			repositorioCategoria.save(Categoria);
		}
		else {
			BadResourceException  exc = new BadResourceException ("Falha ao salvar categoria");
			exc.addErrorMessages("Categoria está nulo ou em branco");
			throw exc;
		}
	}
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsById(id)) {
			throw new  ResourceNotFoundException("Categoria não encontrado com o id: "+id);
		}
		else {
			repositorioCategoria.deleteById(id);
		}
	}
	
	public Long count(){
		return repositorioCategoria.count();
	}
}
