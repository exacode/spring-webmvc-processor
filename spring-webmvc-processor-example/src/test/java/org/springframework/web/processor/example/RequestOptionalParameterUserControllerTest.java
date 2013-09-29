package org.springframework.web.processor.example;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.web.processor.routing.MvcRouting;

public class RequestOptionalParameterUserControllerTest {
	public static final int[] USER_IDS = { 1, 2, 3 };

	@Test
	public void shouldGenerateGetUserById() {
		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUserById().mvc())
				.isEqualTo(new MvcRouting("/users"));
		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUserById()
						.id(USER_IDS[0]).mvc()).isEqualTo(
				new MvcRouting("/users?id=" + USER_IDS[0]));
	}

	@Test
	public void shouldGenerateGetUserByIds() {
		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUserByIds().mvc())
				.isEqualTo(new MvcRouting("/users"));
		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUserByIds()
						.ids(USER_IDS).mvc()).isEqualTo(
				new MvcRouting("/users?id=" + USER_IDS[0] + "&id="
						+ USER_IDS[1] + "&id=" + USER_IDS[2]));
	}

	@Test
	public void shouldGenerateGetUsersWithQuery() {
		String query = "abc";

		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUsersWithQuery()
						.mvc()).isEqualTo(new MvcRouting("/users"));
		Assertions.assertThat(
				RequestOptionalParameterUserController_.getUsersWithQuery()
						.query(query).mvc()).isEqualTo(
				new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithQueryNoValue() {
		String query = "abc";

		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithQueryNoValue().mvc()).isEqualTo(
				new MvcRouting("/users"));
		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithQueryNoValue().query(query).mvc())
				.isEqualTo(new MvcRouting("/users?query=" + query));
	}

	@Test
	public void shouldGenerateGetUsersWithProperties() {
		String prop1 = "abc";
		String prop2 = "def";

		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeProperties().mvc()).isEqualTo(
				new MvcRouting("/users"));
		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeProperties().prop1(prop1).mvc())
				.isEqualTo(new MvcRouting("/users?prop1=" + prop1));
		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeProperties().prop2(prop2).mvc())
				.isEqualTo(new MvcRouting("/users?prop2=" + prop2));
		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeProperties().prop1(prop1).prop2(prop2)
						.mvc()).isEqualTo(
				new MvcRouting("/users?prop1=" + prop1 + "&prop2=" + prop2));
	}

	@Test
	public void shouldGenerateGetUsersWithSomeRequiredProperties() {
		String prop1 = "abc";
		String prop2 = "def";

		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeRequiredProperties(prop1).mvc())
				.isEqualTo(new MvcRouting("/users?prop1=" + prop1));
		Assertions.assertThat(
				RequestOptionalParameterUserController_
						.getUsersWithSomeRequiredProperties(prop1).prop2(prop2)
						.mvc()).isEqualTo(
				new MvcRouting("/users?prop1=" + prop1 + "&prop2=" + prop2));
	}
}
