package org.ranceworks.nginxlog;

import javax.sql.DataSource;

import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@Autowired
	private JdbcTemplate template;

	@RequestMapping("/login")
	public String login() {
		Object a =  template.queryForList("select * from ranceworks.access_log limit 1;");
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
