package org.springframework.web.processor;

import org.springframework.core.convert.ConversionService;

public class RequestMappingProcessorConfiguration {

	public static ConversionService CONVERSION_SERVICE;

	public static String SERVER_URL = "";

	public static String SERVLET_PATH = "";

	public static String getAbsoluteServletUrl() {
		return SERVER_URL + SERVLET_PATH;
	}

}
