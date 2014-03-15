package net.exacode.spring.web.processor.shared;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.ConversionService;

public class RequestMappingProcessorConfiguration implements
		ApplicationListener<ContextRefreshedEvent> {

	private static RequestMappingProcessorConfiguration instance;

	private static ConversionService CONVERSION_SERVICE;

	private static String SERVER_URL = "";

	private static String SERVLET_PATH = "";

	public static String getServletUrl() {
		ensureInitialized();
		return SERVER_URL + getServletPath();
	}

	public static String getServerUrl() {
		ensureInitialized();
		return SERVER_URL;
	}

	public static String getServletPath() {
		ensureInitialized();
		return SERVLET_PATH;
	}

	public static ConversionService getConversionService() {
		ensureInitialized();
		return CONVERSION_SERVICE;
	}

	public static RequestMappingProcessorConfiguration init(String serverUrl,
			String servletPath) {
		if (instance == null) {
			instance = new RequestMappingProcessorConfiguration(serverUrl,
					servletPath);
		}
		return instance;
	}

	private static void ensureInitialized() {
		if (instance == null) {
			throw new IllegalStateException(
					"Before using SpringMVC annotation processor, please initialize: "
							+ RequestMappingProcessorConfiguration.class
									.getName());
		}
	}

	private RequestMappingProcessorConfiguration(String serverUrl,
			String servletPath) {
		SERVER_URL = serverUrl;
		SERVLET_PATH = servletPath;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		CONVERSION_SERVICE = event.getApplicationContext().getBean(
				ConversionService.class);
	}
}
