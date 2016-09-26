package org.ranceworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class NginxLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(NginxLogApplication.class, args);
	}
	
	@RequestMapping("/")
	public String hello() {
		return "hello";
	}
	
}

