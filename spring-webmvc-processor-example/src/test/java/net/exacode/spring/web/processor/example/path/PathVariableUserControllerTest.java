package net.exacode.spring.web.processor.example.path;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.junit.Test;

public class PathVariableUserControllerTest {
	private static final int USER_ID = 1;

	@Test
	public void shouldGenerateGetUser() {
		assertThat(PathVariableUserController_.getUser(USER_ID).mvc())
				.isEqualTo(new MvcRouting("/users/" + USER_ID));
	}

	@Test
	public void shouldGenerateGetUserAccount() {
		assertThat(PathVariableUserController_.getUserAccount(USER_ID).mvc())
				.isEqualTo(new MvcRouting("/users/" + USER_ID + "/account"));
	}
}
