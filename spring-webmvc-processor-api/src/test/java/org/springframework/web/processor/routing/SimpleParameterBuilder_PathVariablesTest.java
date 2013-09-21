package org.springframework.web.processor.routing;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.web.processor.routing.UriBuilder.SimpleUriBuilder;

public class SimpleParameterBuilder_PathVariablesTest {

	@Test
	public void shouldBuildRoutingWithPathVariables() {
		// given
		String uri = "/account/{user}/{opt}";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addPathVariable("user", "john-doe").addPathVariable("opt",
				"opt-val");

		// then
		Assertions.assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe/opt-val");
	}

}
