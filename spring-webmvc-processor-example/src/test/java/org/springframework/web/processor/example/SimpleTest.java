package org.springframework.web.processor.example;

import org.junit.Test;

public class SimpleTest extends BaseSpringTest {

	@Test
	public void test() {
		MappedController_.getUsers().mvc().redirect();
	}

}
