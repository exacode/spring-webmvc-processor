package net.exacode.spring.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestParameterUserController {

	@RequestMapping("/users")
	public void getUsers() {
	}

	@RequestMapping("/users")
	public void getUsersWithQuery(@RequestParam("query") String query) {
	}

	@RequestMapping("/users")
	public void getUsersWithQueryNoValue(@RequestParam String query) {
	}

	@RequestMapping("/users")
	public void getUsersWithSomeProperties(@RequestParam("prop1") String prop1,
			@RequestParam("prop2") String prop2) {
	}

	@RequestMapping("/users")
	public void getUserById(@RequestParam("id") int id) {
	}

	@RequestMapping("/users")
	public void getUserByIds(@RequestParam(value = "id") int[] ids) {
	}

}
