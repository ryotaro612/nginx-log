package org.ranceworks.nginxlog;

import java.util.List;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.services.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private AccessLogService service;

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
	public String dashboard(Model model) {
		List<AccessLog> logs = service.findAll();
		model.addAttribute("accesses", logs);
		return "dashboard";
	}
}
