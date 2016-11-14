package org.ranceworks.nginxlog;

import java.util.List;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.repositories.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private JdbcTemplate template;
	@Autowired
	private AccessLogRepository repository;

	@RequestMapping("/login")
	public String login() {
		/*
		 * new Specification<AccessLog>() {
		 * 
		 * @Override public Predicate toPredicate(Root<AccessLog> root,
		 * CriteriaQuery<?> query, CriteriaBuilder cb) { // TODO Auto-generated
		 * method stub return null; } };
		 */
		Object b = template.queryForList("select * from ranceworks.access_log");
		List<AccessLog> logs = repository.findByCountryCode("JPN");
		long a = repository.count();
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
