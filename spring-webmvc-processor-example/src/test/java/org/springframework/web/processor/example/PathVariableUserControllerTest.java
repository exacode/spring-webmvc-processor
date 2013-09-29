package org.springframework.web.processor.example;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.web.processor.routing.MvcRouting;

public class PathVariableUserControllerTest {
	private static final int USER_ID = 1;

	@Test
	public void shouldGenerateGetUser() {
		Assertions.assertThat(
				PathVariableUserController_.getUser(USER_ID).mvc()).isEqualTo(
				new MvcRouting("/users/" + USER_ID));
	}

	@Test
	public void shouldGenerateGetUserAccount() {
		Assertions.assertThat(
				PathVariableUserController_.getUserAccount(USER_ID).mvc())
				.isEqualTo(new MvcRouting("/users/" + USER_ID + "/account"));
	}
}
