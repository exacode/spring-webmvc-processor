package net.exacode.spring.web.processor.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class MatrixVariableUserControllerTest {
	public static final Integer[] USER_IDS = { 1, 2, 3 };

	@Test
	public void shouldGenerateGetUserById() {
		Assertions.assertThat(
				MatrixVariableUserController_.getUserById(USER_IDS[0]).mvc())
				.isEqualTo(new MvcRouting("/users;id=" + USER_IDS[0]));
	}

	@Test
	public void shouldGenerateGetUserByIds() {
		Assertions.assertThat(
				MatrixVariableUserController_.getUserByIds(
						Arrays.asList(USER_IDS)).mvc()).isEqualTo(
				new MvcRouting("/users;id=" + USER_IDS[0] + "," + USER_IDS[1]
						+ "," + USER_IDS[2]));
	}

	@Test
	public void shouldGenerateGetUserAccount() {
		Assertions.assertThat(
				MatrixVariableUserController_.getUserAccount(USER_IDS[0],
						USER_IDS[0]).mvc()).isEqualTo(
				new MvcRouting("/users/" + USER_IDS[0] + ";id=" + USER_IDS[0]
						+ "/account"));
	}

	@Test
	public void shouldGenerateGetUserAccounts() {
		Assertions.assertThat(
				MatrixVariableUserController_.getUserAccounts(USER_IDS[0],
						Arrays.asList(USER_IDS)).mvc()).isEqualTo(
				new MvcRouting("/users/" + USER_IDS[0] + ";id=" + USER_IDS[0]
						+ "," + USER_IDS[1] + "," + USER_IDS[2] + "/account"));
	}

	@Test
	public void shouldGenerateGetUserResources() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("a", Arrays.asList("three", "four"));
		map.put("b", Arrays.asList("five"));

		Assertions.assertThat(
				MatrixVariableUserController_.getUserResource(map).mvc())
				.isEqualTo(
						new MvcRouting("/users/;b=five;a=three,four/resource"));
	}
}
