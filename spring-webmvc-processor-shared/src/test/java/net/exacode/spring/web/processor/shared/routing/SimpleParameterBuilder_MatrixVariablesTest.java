package net.exacode.spring.web.processor.shared.routing;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;

import org.junit.Test;

public class SimpleParameterBuilder_MatrixVariablesTest {

	@Test
	public void shouldBuildRoutingWithMatrixVariables() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addMatrixVariable("q", 1, 2, 3).addMatrixVariable("p", 3, 4, 5);

		// then
		assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe;q=1,2,3;p=3,4,5");
	}

	@Test
	public void shouldBuildRoutingWithMatrixVariablesInPath() {
		// given
		String uri = "/account/{user}/profile";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addPathVariable("user", "john-doe")
				.addMatrixVariable("user", "q", 1, 2, 3)
				.addMatrixVariable("user", "p", 3, 4, 5)
				.addMatrixVariable("r", 5, 6, 7);

		// then
		assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe;q=1,2,3;p=3,4,5/profile;r=5,6,7");
	}

	@Test
	public void shouldBuildRoutingWithQueryParameters() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);
		builder.addRequestParameter("param", "param-value").addMatrixVariable(
				"r", 5, 6, 7);

		// then
		assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:/account/john-doe;r=5,6,7?param=param-value");
	}
}
