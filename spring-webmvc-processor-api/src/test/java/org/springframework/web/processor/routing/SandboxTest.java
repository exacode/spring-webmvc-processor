package org.springframework.web.processor.routing;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SandboxTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void uriPath() {
		String uriTemplate = "users/{userId}/user";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(uriTemplate);
		UriComponents uriComponents = uriComponentsBuilder.build();

		logger.info("{}", uriComponents.getPathSegments());
	}
}
