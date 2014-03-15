package net.exacode.spring.web.processor.shared.routing;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;

import org.junit.Test;

public class SimpleParameterBuilderTest extends BaseSpringTest {

	@Test
	public void shouldBuildSimpleMvcRouting() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);

		// then
		assertThat(builder.mvc().forward()).isEqualTo("forward:" + uri);
		assertThat(builder.mvc().redirect()).isEqualTo("redirect:" + uri);
	}

	@Test
	public void shouldBuildSimpleUrlRouting() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);

		// then
		assertThat(builder.url().absolute()).isEqualTo(
				TestConfiguration.SERVER_URL + TestConfiguration.SERVLET_PATH
						+ uri);
		assertThat(builder.url().relative().server()).isEqualTo(
				TestConfiguration.SERVLET_PATH + uri);
		assertThat(builder.url().relative().servlet()).isEqualTo(uri);
	}

}
