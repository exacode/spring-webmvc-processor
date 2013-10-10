package net.exacode.spring.web.processor.shared.routing;

import net.exacode.spring.web.processor.shared.RequestMappingProcessorConfiguration;
import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class SimpleParameterBuilderTest {

	@Test
	public void shouldBuildSimpleMvcRouting() {
		// given
		String uri = "/account/john-doe";

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);

		// then
		Assertions.assertThat(builder.mvc().forward()).isEqualTo(
				"forward:" + uri);
		Assertions.assertThat(builder.mvc().redirect()).isEqualTo(
				"redirect:" + uri);
	}

	@Test
	public void shouldBuildSimpleUrlRouting() {
		// given
		String uri = "/account/john-doe";
		String server = "http://localhost:8080";
		String servlet = "/app";
		RequestMappingProcessorConfiguration.init(server, servlet);

		// when
		SimpleUriBuilder builder = new SimpleUriBuilder(uri);

		// then
		Assertions.assertThat(builder.url().absolute()).isEqualTo(
				server + servlet + uri);
		Assertions.assertThat(builder.url().relative().server()).isEqualTo(
				servlet + uri);
		Assertions.assertThat(builder.url().relative().servlet())
				.isEqualTo(uri);
	}

}
