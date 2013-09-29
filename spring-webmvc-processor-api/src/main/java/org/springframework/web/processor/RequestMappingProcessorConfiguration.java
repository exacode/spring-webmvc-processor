package org.springframework.web.processor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.ConversionService;

public class RequestMappingProcessorConfiguration implements
		ApplicationListener<ContextRefreshedEvent> {

	private static RequestMappingProcessorConfiguration instance;

	private static ConversionService CONVERSION_SERVICE;

	private static String SERVER_URL = "";

	private static String SERVLET_PATH = "";

	public static String getAbsoluteServletUrl() {
		return SERVER_URL + SERVLET_PATH;
	}

	public static String getServletUrl() {
		return SERVER_URL + SERVLET_PATH;
	}

	public static String getServletPath() {
		return SERVLET_PATH;
	}

	public static ConversionService getConversionService() {
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
