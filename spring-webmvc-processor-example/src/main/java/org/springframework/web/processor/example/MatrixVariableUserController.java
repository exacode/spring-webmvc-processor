package org.springframework.web.processor.example;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatrixVariableUserController {

	@RequestMapping("/users")
	public void getUserById(@MatrixVariable("id") int id) {
	}

	@RequestMapping("/users")
	public void getUserByIds(@MatrixVariable("id") List<Integer> ids) {
	}

	@RequestMapping("/users/{userId}/account")
	public void getUserAccount(@PathVariable int userId,
			@MatrixVariable(value = "id", pathVar = "userId") int id) {
	}

	@RequestMapping("/users/{userIds}/account")
	public void getUserAccounts(@PathVariable int userIds,
			@MatrixVariable(value = "id", pathVar = "userIds") List<Integer> ids) {
	}

	@RequestMapping(value = "/users/{resourceId}/resource")
	public void getUserResource(
			@MatrixVariable Map<String, String> matrixVars,
			@MatrixVariable(pathVar = "resourceId") Map<String, List<String>> resourceMatrixVars) {
	}

}
