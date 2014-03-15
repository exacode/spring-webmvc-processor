package net.exacode.spring.web.processor.example.request;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.example.BaseSpringTest;
import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.junit.Test;

public class RequestParameterUserControllerTest extends BaseSpringTest {
	public static final int[] USER_IDS = { 1, 2, 3 };

	@Test
	public void shouldGenerateGetUsers() {
		assertThat(RequestParameterUserController_.getUsers().mvc()).isEqualTo(
				new MvcRouting("/users"));
	}

	@Test
	public void shouldGenerateGetUserById() {
		assertThat(
				RequestParameterUserController_.getUserById(USER_IDS[0]).mvc())
				.isEqualTo(new MvcRouting("/users?id=" + USER_IDS[0]));
	}

	@Test
	public void shouldGenerateGetUserByIds() {
		assertThat(RequestParameterUserController_.getUserByIds(USER_IDS).mvc())
				.isEqualTo(
						new MvcRouting("/users?id=" + USER_IDS[0] + "&id="
								+ USER_IDS[1] + "&id=" + USER_IDS[2]));
	}

	@Test
	public void shouldGenerateGetUsersWithQuery() {
		String query = "abc";

		assertThat(
				RequestParameterUserController_.getUsersWithQuery(query).mvc())
				.isEqualTo(new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithQueryNoValue() {
		String query = "abc";

		assertThat(
				RequestParameterUserController_.getUsersWithQueryNoValue(query)
						.mvc()).isEqualTo(
				new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithProperties() {
		String prop1 = "abc";
		String prop2 = "def";

		assertThat(
				RequestParameterUserController_.getUsersWithSomeProperties(
						prop1, prop2).mvc()).isEqualTo(
				new MvcRouting("/users?prop1=" + prop1 + "&prop2=" + prop2));
	}
}
