package net.exacode.spring.web.processor.shared.routing;

/**
 * Represents SpringMVC routing.
 * <p>
 * Builds SpringMVC routing values like forwards and redirects.
 * 
 * @author mendlik
 * 
 */
public class MvcRouting {

	public static final String FORWARD_MVC_PREFIX = "forward:";
	public static final String REDIRECT_MVC_PREFIX = "redirect:";
	private final String uri;

	public MvcRouting(String uri) {
		this.uri = uri;
	}

	/**
	 * 
	 * @return Redirect to URI. Example: {@code "redirect:/uri" }
	 */
	public String redirect() {
		return REDIRECT_MVC_PREFIX + uri;
	}

	/**
	 * 
	 * @return Forward to URI. Example: {@code "forward:/uri" }
	 */
	public String forward() {
		return FORWARD_MVC_PREFIX + uri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MvcRouting other = (MvcRouting) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MvcRouting [uri=" + uri + "]";
	}

}