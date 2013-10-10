package net.exacode.spring.web.processor.shared.routing;

import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class SimpleParameterBuilder_RequestParametersTest {

	@Test
	public void shouldBuildRoutingWithQueryParameters() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addRequestParameter("param", "param-val").addRequestParameter(
				"param2", "param2-val");

		// then
		Assertions.assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe?param=param-val&param2=param2-val");
	}

}
