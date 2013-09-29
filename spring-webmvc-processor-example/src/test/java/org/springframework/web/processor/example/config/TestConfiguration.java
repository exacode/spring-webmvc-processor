package org.springframework.web.processor.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.processor.RequestMappingProcessorConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.springframework.web.processor.example")
public class TestConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public RequestMappingProcessorConfiguration requestMappingProcessorConfiguration() {
		return RequestMappingProcessorConfiguration.init(
				"http://localhost:8080", "/myapp");
	}

}
