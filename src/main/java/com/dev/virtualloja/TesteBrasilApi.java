package com.dev.virtualloja;

import org.springframework.web.client.RestTemplate;

import com.dev.virtualloja.domain.Endereco;

public class TesteBrasilApi {
	
	public static void main(String[] args) {
		String cep = "87711360";
		String url = "https://brasilapi.com.br/api/cep/v2/"+cep;
		RestTemplate restTemplate = new RestTemplate();
		Endereco ob = restTemplate.getForObject(url, Endereco.class);
		System.out.print(ob);
		
	}

}
