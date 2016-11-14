package org.ranceworks.nginxlog.services;

import java.util.List;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.repositories.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {
	@Autowired
	private AccessLogRepository repository;

	public List<AccessLog> findAll() {
		/*
		 * Specification<> new Specification<AccessLog>() {
		 * 
		 * @Override public Predicate toPredicate(Root<AccessLog> root,
		 * CriteriaQuery<?> query, CriteriaBuilder cb) { return
		 * cb.equal(root.get(AccessLog_.countryCode), "JPN"); } };
		 */
		// Object b = template.queryForList("select * from
		// ranceworks.access_log");
		// List<AccessLog> logs = repository.findByCountryCode("JPN");
		// long a = repository.count();
		return repository.findAll();
	}
}
