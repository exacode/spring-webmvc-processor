package net.exacode.spring.web.processor.example;

import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class OmittedParameterUserControllerTest {
	@Test
	public void shouldGenerateNoParameterMappingForMatrixMap() {
		Assertions.assertThat(
				OmittedParameterUserController_.getUsersWithMatrixVariables()
						.mvc()).isEqualTo(new MvcRouting("/users"));
	}

	@Test
	public void shouldGenerateNoParameterMappingForPathVariablesMap() {
		Assertions.assertThat(
				OmittedParameterUserController_.getUsersWithPathVariables()
						.addPathVariable("var1", "varVal1")
						.addPathVariable("var2", "varVal2").mvc()).isEqualTo(
				new MvcRouting("/users/varVal1/varVal2"));
	}

	@Test
	public void shouldGenerateNoParameterMappingForRequestParamsMap() {
		Assertions.assertThat(
				OmittedParameterUserController_.getUsersWithRequestParams()
						.mvc()).isEqualTo(new MvcRouting("/users"));
	}
}
