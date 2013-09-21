package org.springframework.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class MappedController {

	@RequestMapping("/{userId}")
	public String getUser(@PathVariable("userId") String userId) {
		return "user.ftl";
	}

}
