package com.dev.virtualloja.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class ProdutoDomain implements Serializable{
	
	private static final long serialVersionUID = 4048798961366546485L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	private String nome;
	
	private Double valorVenda;
	private Double valorCusto;
	private Integer quantidade;
	
	
	@Column(length = 4000)
	private String descricao;
	

	@ManyToOne
	@JoinColumn(name = "idMarca")
	private Marca marca;

	@ManyToOne
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;
	

}
