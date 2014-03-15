package net.exacode.spring.web.processor.shared.routing;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;

import org.junit.Test;

public class SimpleParameterBuilder_PathVariablesTest extends BaseSpringTest {

	@Test
	public void shouldBuildRoutingWithPathVariables() {
		// given
		String uri = "/account/{user}/{opt}";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addPathVariable("user", "john-doe").addPathVariable("opt",
				"opt-val");

		// then
		assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe/opt-val");
	}

}
