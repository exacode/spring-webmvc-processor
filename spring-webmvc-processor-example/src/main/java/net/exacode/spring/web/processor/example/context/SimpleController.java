package net.exacode.spring.web.processor.example.context;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/context")
public class SimpleController {

	@RequestMapping("/hello")
	public void hello() {
	}

}
