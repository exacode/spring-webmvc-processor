package org.springframework.web.processor.example;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SimpleController {

	@RequestMapping("/users")
	public String getUsers() {
		return "users.ftl";
	}

	@RequestMapping("/users")
	public String getUsers(@RequestParam("search") String query) {
		return "users.ftl";
	}

	@RequestMapping("/users2")
	public String getUsers(@RequestParam("id") int id) {
		return "users.ftl";
	}

	@RequestMapping("/users")
	public String getUsers(@MatrixVariable("id") List<String> userIds) {
		return "users.ftl";
	}

	@RequestMapping("/usersX")
	public String getUsersX(@MatrixVariable("id") List<? extends Model> userIds) {
		return "users.ftl";
	}

	@RequestMapping("/usersX3")
	public <W extends Model> String getUsersX3(
			@MatrixVariable("id") List<W> userIds) {
		return "users.ftl";
	}

	@RequestMapping("/usersX4")
	public <W extends Model> String getUsersX4(
			@MatrixVariable("id") List<List<W>> userIds) {
		return "users.ftl";
	}

	@RequestMapping("/users")
	public String getSingleUser(@MatrixVariable("id") String userId) {
		return "users.ftl";
	}

	@RequestMapping("/users/{userId}")
	public String getUser(@PathVariable("userId") String userId) {
		return "users.ftl";
	}

	public void someNotMappedMethod() {
		// deliberately empty
	}
}
