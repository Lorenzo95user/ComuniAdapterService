package dev.newtech.myapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfigurator{
	
	
	@Bean
	RestClient restClient() {
		return RestClient.builder().baseUrl("http://localhost:9090/api").build();
	}

}
