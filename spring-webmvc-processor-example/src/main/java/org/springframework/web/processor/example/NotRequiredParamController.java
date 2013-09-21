package org.springframework.web.processor.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotRequiredParamController {

	@RequestMapping("/users")
	public String user(@RequestParam(required = false) String userId) {
		return "users/user.ftl";
	}

	@RequestMapping("/users")
	public String user(@RequestParam(required = false) Integer userId) {
		return "users/user.ftl";
	}

}
