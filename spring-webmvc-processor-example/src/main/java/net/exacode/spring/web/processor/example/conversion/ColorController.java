package net.exacode.spring.web.processor.example.conversion;

import java.awt.Color;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ColorController {

	@RequestMapping("/image")
	public void getImage(@RequestParam Color color) {

	}

}
