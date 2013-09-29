package org.springframework.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MixedUserController {

	@RequestMapping("/users/{resource}")
	public void getUsers(@PathVariable("resource") String resource,
			@RequestParam("query") String query, @MatrixVariable int[] q) {
	}

}
