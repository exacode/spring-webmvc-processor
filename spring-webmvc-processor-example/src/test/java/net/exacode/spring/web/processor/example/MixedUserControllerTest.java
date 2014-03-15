package net.exacode.spring.web.processor.example;

import static org.assertj.core.api.Assertions.assertThat;
import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.junit.Test;

public class MixedUserControllerTest extends BaseSpringTest {

	public static final int[] USER_IDS = { 1, 2, 3 };

	@Test
	public void shouldGenerateGetUserAccounts() {
		String resource = "image";
		String query = "some-query";

		assertThat(
				MixedUserController_.getUsers(resource, query, USER_IDS).mvc())
				.isEqualTo(
						new MvcRouting("/users/" + resource + ";q="
								+ USER_IDS[0] + "," + USER_IDS[1] + ","
								+ USER_IDS[2] + "?query=" + query));
	}
}
