package net.exacode.spring.web.processor.example;

import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class RequestParameterUserControllerTest {
	public static final int[] USER_IDS = { 1, 2, 3 };

	@Test
	public void shouldGenerateGetUsers() {
		Assertions.assertThat(RequestParameterUserController_.getUsers().mvc())
				.isEqualTo(new MvcRouting("/users"));
	}

	@Test
	public void shouldGenerateGetUserById() {
		Assertions.assertThat(
				RequestParameterUserController_.getUserById(USER_IDS[0]).mvc())
				.isEqualTo(new MvcRouting("/users?id=" + USER_IDS[0]));
	}

	@Test
	public void shouldGenerateGetUserByIds() {
		Assertions.assertThat(
				RequestParameterUserController_.getUserByIds(USER_IDS).mvc())
				.isEqualTo(
						new MvcRouting("/users?id=" + USER_IDS[0] + "&id="
								+ USER_IDS[1] + "&id=" + USER_IDS[2]));
	}

	@Test
	public void shouldGenerateGetUsersWithQuery() {
		String query = "abc";

		Assertions.assertThat(
				RequestParameterUserController_.getUsersWithQuery(query).mvc())
				.isEqualTo(new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithQueryNoValue() {
		String query = "abc";

		Assertions.assertThat(
				RequestParameterUserController_.getUsersWithQueryNoValue(query)
						.mvc()).isEqualTo(
				new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithProperties() {
		String prop1 = "abc";
		String prop2 = "def";

		Assertions.assertThat(
				RequestParameterUserController_.getUsersWithSomeProperties(
						prop1, prop2).mvc()).isEqualTo(
				new MvcRouting("/users?prop1=" + prop1 + "&prop2=" + prop2));
	}
}
