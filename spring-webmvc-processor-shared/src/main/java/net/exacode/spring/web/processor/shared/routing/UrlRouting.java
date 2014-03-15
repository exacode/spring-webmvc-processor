package net.exacode.spring.web.processor.shared.routing;

import net.exacode.spring.web.processor.shared.RequestMappingProcessorConfiguration;

/**
 * Represents URL routing.
 * 
 * @author mendlik
 * 
 */
public class UrlRouting {

	/**
	 * Represents relative url routing.
	 * 
	 * @author mendlik
	 * 
	 */
	public class UrlRelativeValue {
		private final String uri;

		public UrlRelativeValue(String uri) {
			this.uri = uri;
		}

		/**
		 * 
		 * Produces path relative to the server. Example:
		 * {@code /servlet_path/resource_path}
		 * <p>
		 * Server relative path consists of {@code servlet_path} and
		 * {@code resource_path}.
		 * <ul>
		 * <li>{@code servlet_path} is retrieved from configuration
		 * {@code RequestMappingProcessorConfiguration#getServletPath()}
		 * </ul>
		 * 
		 * @see RequestMappingProcessorConfiguration
		 * @return path relative to the server
		 */
		public String server() {
			return RequestMappingProcessorConfiguration.getServletPath() + uri;
		}

		/**
		 * 
		 * Produces path relative to the servlet. Example:
		 * {@code /resource_path}
		 * <p>
		 * Server relative path consists of {@code resource_path} only.
		 * 
		 * @return path relative to the servlet
		 */
		public String servlet() {
			return uri;
		}
	}

	private final String uri;

	public UrlRouting(String uri) {
		this.uri = uri;
	}

	/**
	 * 
	 * Produces absolute path. Example:
	 * {@code http://localhost:8080/servlet_path/resource_path}
	 * <p>
	 * Server relative path consists of {@code host}, {@code servlet_path} and
	 * {@code resource_path}.
	 * <ul>
	 * <li>{@code servlet_path} is retrieved from configuration
	 * {@code RequestMappingProcessorConfiguration#getServletPath()}
	 * <li>{@code host} is retrieved from configuration
	 * {@code RequestMappingProcessorConfiguration#getAbsoluteServletUrl()}
	 * </ul>
	 * 
	 * @see RequestMappingProcessorConfiguration
	 * @return absolute path to the resource/page
	 */
	public String absolute() {
		return RequestMappingProcessorConfiguration.getServletUrl() + uri;
	}

	/**
	 * @return {@link UrlRelativeValue}
	 */
	public UrlRelativeValue relative() {
		return new UrlRelativeValue(uri);
	}
}