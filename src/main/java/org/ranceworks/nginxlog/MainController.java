package org.ranceworks.nginxlog;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.services.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping("/accesses")
	public String dashboard(@RequestParam(value = "country_code", required = false) Optional<String> countryCode,
			@RequestParam(required = false) Optional<String> uri, @RequestParam(required = false) Optional<String> city,
			@RequestParam(required = false, value = "from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> fromDate,
			@RequestParam(required = false, value = "to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> toDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false, value = "per_page") Optional<Integer> perPage, Model model) {
		final int defaultPerPage = 10;
		final int defaultPage = page.orElse(0);

		Page<AccessLog> a = service.findAll(countryCode, uri, city, fromDate, toDate,
				new PageRequest(defaultPage, perPage.orElse(defaultPerPage)));

		List<AccessLog> logs = service.findAll();
		model.addAttribute("accesses", logs);
		return "accesses";
	}
}
