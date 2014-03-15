package net.exacode.spring.web.processor.example.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.example.BaseSpringTest;
import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.junit.Test;

public class OmittedParameterUserControllerTest extends BaseSpringTest {
	@Test
	public void shouldGenerateNoParameterMappingForMatrixMap() {
		assertThat(
				OmittedParameterUserController_.getUsersWithMatrixVariables()
						.mvc()).isEqualTo(new MvcRouting("/users"));
	}

	@Test
	public void shouldGenerateNoParameterMappingForPathVariablesMap() {
		assertThat(
				OmittedParameterUserController_.getUsersWithPathVariables()
						.addPathVariable("var1", "varVal1")
						.addPathVariable("var2", "varVal2").mvc()).isEqualTo(
				new MvcRouting("/users/varVal1/varVal2"));
	}

	@Test
	public void shouldGenerateNoParameterMappingForRequestParamsMap() {
		assertThat(
				OmittedParameterUserController_.getUsersWithRequestParams()
						.mvc()).isEqualTo(new MvcRouting("/users"));
	}
}
