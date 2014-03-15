package net.exacode.spring.web.processor.example.matrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OmittedParameterUserController {
	@RequestMapping(value = "/users")
	public void getUsersWithMatrixVariables(
			@MatrixVariable Map<String, List<String>> matrixVars) {
	}

	@RequestMapping(value = "/users/{var1}/{var2}")
	public void getUsersWithPathVariables(
			@MatrixVariable Map<String, String> pathVars) {
	}

	@RequestMapping(value = "/users")
	public void getUsersWithRequestParams(
			@RequestParam Map<String, List<String>> requestVars) {
	}
}
