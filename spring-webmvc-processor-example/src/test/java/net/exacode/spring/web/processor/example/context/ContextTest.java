package net.exacode.spring.web.processor.example.context;

import net.exacode.spring.web.processor.example.BaseSpringTest;
import net.exacode.spring.web.processor.example.TestConfiguration;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ContextTest extends BaseSpringTest {

	@Test
	public void shouldCreateServerRealtiveUri() {
		Assertions.assertThat(
				SimpleController_.hello().url().relative().server()).isEqualTo(
				TestConfiguration.SERVLET_PATH + "/context/hello");
	}

	@Test
	public void shouldCreateServletRealtiveUri() {
		Assertions.assertThat(
				SimpleController_.hello().url().relative().servlet())
				.isEqualTo("/context/hello");
	}

	@Test
	public void shouldCreateFullUrl() {
		Assertions.assertThat(SimpleController_.hello().url().absolute())
				.isEqualTo(
						TestConfiguration.SERVER_URL
								+ TestConfiguration.SERVLET_PATH
								+ "/context/hello");
	}
}
