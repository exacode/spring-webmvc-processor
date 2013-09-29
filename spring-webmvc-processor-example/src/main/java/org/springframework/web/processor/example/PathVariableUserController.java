package org.springframework.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathVariableUserController {

	@RequestMapping("/users/{userId}")
	public void getUser(@PathVariable("userId") int userId) {
	}

	@RequestMapping("/users/{userId}/account")
	public void getUserAccount(@PathVariable("userId") int userId) {
	}

}
