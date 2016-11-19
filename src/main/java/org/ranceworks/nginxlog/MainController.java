package org.ranceworks.nginxlog;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ranceworks.nginxlog.dto.AccessCount;
import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.services.AccessLogService;
import org.ranceworks.nginxlog.services.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

@Controller
public class MainController {

	@Autowired
	private AccessLogService service;
	@Autowired
	private RankService rankService;

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
		return "redirect:" + ACCESS_LOG_URL;
	}

	private static final String ACCESS_LOG_URL = "/accesses";
	final int DEFAULT_PER_PAGE = 100;
	final int DEFAULT_PAGE = 0;
	private static final String ACCESSRANK_URL = "/accessrank";

	@RequestMapping(ACCESSRANK_URL)
	public String accessRank(
			@RequestParam(required = false, value = "from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> fromDate,
			@RequestParam(required = false, value = "to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> toDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false, value = "per_page") Optional<Integer> perPage, Model model) {

		final int fixPerPage = Math.min(perPage.orElse(DEFAULT_PER_PAGE), DEFAULT_PER_PAGE);
		final int fixPage = page.orElse(DEFAULT_PAGE);
		final Pageable pageable = new PageRequest(fixPage, fixPerPage);

		Page<AccessCount> accessCounts = null;
		if (date.isPresent()) {
			accessCounts = rankService.accessRank(pageable, date.get());
		} else {
			accessCounts = rankService.accessRank(pageable, fromDate, toDate);
		}
		model.addAttribute("accessCounts", accessCounts);

		if (accessCounts.hasPrevious()) {
			model.addAttribute("previousPage", buildAccessRankUrl(fromDate, toDate, date, fixPage - 1, fixPerPage));
		}
		if (accessCounts.hasNext()) {
			model.addAttribute("nextPage", buildAccessRankUrl(fromDate, toDate, date, fixPage + 1, fixPerPage));
		}

		return "accessrank";
	}

	private String buildAccessRankUrl(Optional<Date> fromDate, Optional<Date> toDate, Optional<Date> date, int page,
			int perPage) {
		final List<Optional<String>> params = new ArrayList<>();
		params.add(getAttr("from_date", fromDate.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("to_date", toDate.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("date", date.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("per_page", Optional.of(perPage + "")));
		params.add(Optional.of("page=" + (page)));
		return ACCESSRANK_URL + "?" + String.join("&", String.join("&",
				params.stream().filter(s -> s.isPresent()).map(s -> s.get()).collect(Collectors.toList())));
	}

	@RequestMapping(ACCESS_LOG_URL)
	public String dashboard(@RequestParam(value = "country_code", required = false) Optional<String> countryCode,
			@RequestParam(required = false) Optional<String> uri, @RequestParam(required = false) Optional<String> city,
			@RequestParam(required = false, value = "from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> fromDate,
			@RequestParam(required = false, value = "to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> toDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false, value = "per_page") Optional<Integer> perPage, Model model)
			throws UnsupportedEncodingException {

		final int fixPerPage = Math.min(perPage.orElse(DEFAULT_PER_PAGE), DEFAULT_PER_PAGE);
		final int fixPage = page.orElse(DEFAULT_PAGE);
		final Pageable pageable = new PageRequest(fixPage, fixPerPage);

		Page<AccessLog> found = null;
		if (!countryCode.isPresent() && !uri.isPresent() && !city.isPresent() && !fromDate.isPresent()
				&& !toDate.isPresent() && !date.isPresent()) {
			found = service.findAll(pageable);
		} else if (date.isPresent()) {
			found = service.findAll(countryCode, uri, city, date.get(), pageable);
		} else {
			found = service.findAll(countryCode, uri, city, fromDate, toDate, pageable);
		}

		if (found.hasPrevious()) {
			model.addAttribute("previousPage",
					buildQuery(countryCode, uri, city, date, fromDate, toDate, fixPage - 1, fixPerPage));
		}
		if (found.hasNext()) {
			model.addAttribute("nextPage",
					buildQuery(countryCode, uri, city, date, fromDate, toDate, fixPage + 1, fixPerPage));
		}

		model.addAttribute("accesses", found);
		return "accesses";
	}

	private String buildQuery(Optional<String> countryCode, Optional<String> uri, Optional<String> city,
			Optional<Date> date, Optional<Date> fromDate, Optional<Date> toDate, int page, int perPage)
			throws UnsupportedEncodingException {
		final List<Optional<String>> params = new ArrayList<>();
		params.add(getAttr("country_code", countryCode));
		params.add(getAttr("uri", uri));
		params.add(getAttr("city", city));
		params.add(getAttr("from_date", fromDate.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("to_date", toDate.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("date", date.map(s -> new SimpleDateFormat("yyyy-MM-dd").format(s))));
		params.add(getAttr("per_page", Optional.of(perPage + "")));
		params.add(Optional.of("page=" + (page)));

		return ACCESS_LOG_URL + "?" + String.join("&",
				params.stream().filter(s -> s.isPresent()).map(s -> s.get()).collect(Collectors.toList()));
	}

	private Optional<String> getAttr(String key, Optional<String> val) {
		return val.map(s -> {
			try {
				return key + "=" + UriUtils.encode(s, StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});

	}
}
