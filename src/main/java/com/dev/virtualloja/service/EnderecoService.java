package com.dev.virtualloja.service;

import org.springframework.web.client.RestTemplate;

import com.dev.virtualloja.domain.Endereco;

public class EnderecoService {
	public Endereco getEndereco( String cep) {
		String url = "https://brasilapi.com.br/api/cep/v2/"+cep;
		RestTemplate restTemplate = new RestTemplate();
		Endereco ob = restTemplate.getForObject(url, Endereco.class);
		return ob;
	}
}
