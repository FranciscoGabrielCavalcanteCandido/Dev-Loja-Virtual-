package com.dev.virtualloja.domain;

import lombok.Data;

@Data
public class Endereco {
	private String cep;
	private String state;
	private String city;
	private String neighborhood;
	private String street;
	private Location location;
	
}
