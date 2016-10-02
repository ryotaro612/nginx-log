package org.ranceworks.nginxlog;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
	@RequestMapping("/login")
	public String login() {
		return "signin";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
