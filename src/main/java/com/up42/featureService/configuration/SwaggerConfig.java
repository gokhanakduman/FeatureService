package com.up42.featureService.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration

public class SwaggerConfig {
	
	
	@Bean
	public OpenAPI sopenAPI() {
		return new OpenAPI()
				.info(new Info()
				.title("Feature Service")
				.version("v0.0.1")
				.license(new License()
				.name("Apache 2.0")
				.url("http://springdoc.org")));
	}
	
	
/*
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("springshop-public")
	            .pathsToMatch("/public/**")
	            .build();
	}
	@Bean
	public GroupedOpenApi adminApi() {
	    return GroupedOpenApi.builder()
	    		.group("springshop-admin")
	            .pathsToMatch("/admin/**")
	            .build();
	}
	*/
	
	/*
	@Configuration
	public class SpringFoxConfig {                                    
	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()
	          .apis(RequestHandlerSelectors.basePackage("com.up42.featureService"))              
	          .paths(PathSelectors.any())                          
	          .build().apiInfo(apiEndPointsInfo())
	          ;                                           
	    }
	}

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Feature Service")
                .description("Feature Servie Documentation")
                .version("1.0.0")
                .build();
    }
*/
}