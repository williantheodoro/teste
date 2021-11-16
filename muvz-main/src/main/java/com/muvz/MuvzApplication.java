package com.muvz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class MuvzApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuvzApplication.class, args);
	}
	
	@GetMapping
	public ModelAndView swaggerUi() {
		return new ModelAndView("redirect:/swagger-ui/");
	}

}
