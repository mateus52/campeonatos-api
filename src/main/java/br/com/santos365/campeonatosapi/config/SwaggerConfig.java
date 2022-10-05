package br.com.santos365.campeonatosapi.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final String BASE_PACKAGE = "br.com.santos365.campeonatosapi.controller";
	private static final String API_TITULO = "Campeonatos API";
	private static final String API_DESCRICAO = "API Rest - Obtem dados partidas esportivas";
	private static final String API_VERSAO = "1.0.0";
	private static final String NOME_CONTATO = "Mateus Alves";
	private static final String GITHUB_CONTATO = "https://github.com/mateus52";
	private static final String EMAIL_CONTATO = "mateusdossantos52@gmail.com";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(basePackage(BASE_PACKAGE))
				.paths(PathSelectors.any()).build()
				.apiInfo(buidApiInfo());
	}
	
	public ApiInfo buidApiInfo() {
		return new ApiInfoBuilder().title(API_TITULO)
				.description(API_DESCRICAO)
				.version(API_VERSAO)
				.contact( new Contact(NOME_CONTATO, GITHUB_CONTATO, EMAIL_CONTATO))
				.build();
	}
	

}
