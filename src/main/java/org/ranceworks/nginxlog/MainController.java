package org.ranceworks.nginxlog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/login")
	public String login() {
		return "signin";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}
