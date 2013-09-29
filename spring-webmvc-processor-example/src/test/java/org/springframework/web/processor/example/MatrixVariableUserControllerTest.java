package org.springframework.web.processor.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.web.processor.routing.MvcRouting;

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
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "one");
		map.put("p", "two");
		Map<String, List<String>> map2 = new HashMap<String, List<String>>();
		map2.put("a", Arrays.asList("three", "four"));
		map2.put("b", Arrays.asList("five"));

		Assertions
				.assertThat(
						MatrixVariableUserController_
								.getUserResource(map, map2).mvc())
				.isEqualTo(
						new MvcRouting(
								"/users/;b=five;a=three,four/resource;q=one;p=two"));
	}
}
