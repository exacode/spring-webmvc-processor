package org.springframework.web.processor.routing;

public class MvcRouting {

	public static final String FORWARD_MVC_PREFIX = "forward:";
	public static final String REDIRECT_MVC_PREFIX = "redirect:";
	private final String uri;

	public MvcRouting(String uri) {
		this.uri = uri;
	}

	public String redirect() {
		return REDIRECT_MVC_PREFIX + uri;
	}

	public String forward() {
		return FORWARD_MVC_PREFIX + uri;
	}
}