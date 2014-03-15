package net.exacode.spring.web.processor.example;

import net.exacode.spring.web.processor.shared.RequestMappingProcessorConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackageClasses = TestConfiguration.class)
public class TestConfiguration extends WebMvcConfigurerAdapter {

	public static String SERVER_URL = "http://localhost:8080";

	public static String SERVLET_PATH = "/myapp";

	@Bean
	public RequestMappingProcessorConfiguration requestMappingProcessorConfiguration() {
		return RequestMappingProcessorConfiguration.init(SERVER_URL,
				SERVLET_PATH);
	}

}
