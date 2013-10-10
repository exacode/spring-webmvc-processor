package net.exacode.spring.web.processor.shared.routing;

import net.exacode.spring.web.processor.shared.RequestMappingProcessorConfiguration;

public class UrlRouting {

	public class UrlRelativeValue {
		private final String uri;

		public UrlRelativeValue(String uri) {
			this.uri = uri;
		}

		public String server() {
			return RequestMappingProcessorConfiguration.getServletPath() + uri;
		}

		public String servlet() {
			return uri;
		}
	}

	private final String uri;

	public UrlRouting(String uri) {
		this.uri = uri;
	}

	public String absolute() {
		return RequestMappingProcessorConfiguration.getAbsoluteServletUrl()
				+ uri;
	}

	public UrlRelativeValue relative() {
		return new UrlRelativeValue(uri);
	}
}