package com.otaviowalter.stockin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("StockIN").version("1.0").description(
				"Sistema de gerenciamento de estoque com PDV, desenvolvido como projeto pessoal, incluindo controle de compras, vendas, reposição e auditoria completa de movimentações, devoluções e alterações de produtos. Implementa autenticação JWT stateless e segue princípios de Clean Code, com foco em organização, manutenibilidade e robustez."));
	}
}
