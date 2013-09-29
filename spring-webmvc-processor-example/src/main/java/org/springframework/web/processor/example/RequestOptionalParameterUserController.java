package org.springframework.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestOptionalParameterUserController {

	@RequestMapping("/users")
	public void getUsersWithQuery(
			@RequestParam(value = "query", required = false) String query) {
	}

	@RequestMapping("/users")
	public void getUsersWithQueryNoValue(
			@RequestParam(required = false) String query) {
	}

	@RequestMapping("/users")
	public void getUsersWithSomeProperties(
			@RequestParam(value = "prop1", required = false) String prop1,
			@RequestParam(value = "prop2", required = false) String prop2) {
	}

	@RequestMapping("/users")
	public void getUsersWithSomeRequiredProperties(
			@RequestParam(value = "prop1") String prop1,
			@RequestParam(value = "prop2", required = false) String prop2) {
	}

	@RequestMapping("/users")
	public void getUserById(@RequestParam(value = "id", required = false) int id) {
	}

	@RequestMapping("/users")
	public void getUserByIds(
			@RequestParam(value = "id", required = false) int[] ids) {
	}

}
