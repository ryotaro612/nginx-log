package org.ranceworks.nginxlog.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.ranceworks.nginxlog.dto.AccessCount;
import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.entities.AccessLog_;
import org.ranceworks.nginxlog.repositories.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RankService {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AccessLogRepository repository;

	/**
	 * ref: http://www.thejavageek.com/2014/04/28/criteria-group-clause/
	 */
	public Page<AccessCount> accessRank(Pageable pageable) {

		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		final Root<AccessLog> root = query.from(AccessLog.class);
		query.multiselect(root.get(AccessLog_.uri), cb.count(root.get(AccessLog_.uri)))
				.groupBy(root.get(AccessLog_.uri));

		return new PageImpl<AccessCount>(entityManager.createQuery(query).getResultList().stream().map(f -> {
			AccessCount ac = new AccessCount();
			ac.setUrl((String) f[0]);
			ac.setCount((Long) f[1]);
			return ac;
		}).collect(Collectors.toList()), pageable, repository.count());
	}

	public Page<AccessCount> accessRank(Pageable pageable, Date date) {
		return accessRank(pageable, date, date);
	}

	public Page<AccessCount> accessRank(Pageable pageable, Optional<Date> fromDate, Optional<Date> toDate) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		final Root<AccessLog> root = query.from(AccessLog.class);

		final List<Predicate> p = java.util.Arrays
				.asList(fromDate.map(d -> cb.greaterThanOrEqualTo(root.get(AccessLog_.dateGmt), d)),
						toDate.map(d -> cb.lessThanOrEqualTo(root.get(AccessLog_.dateGmt), d)))
				.stream().filter(f -> f.isPresent()).map(Optional::get).collect(Collectors.toList());

		query.multiselect(root.get(AccessLog_.uri), cb.count(root.get(AccessLog_.uri))).where((Predicate[]) p.toArray())
				.groupBy(root.get(AccessLog_.uri));

		return new PageImpl<AccessCount>(entityManager.createQuery(query).getResultList().stream().map(f -> {
			AccessCount ac = new AccessCount();
			ac.setUrl((String) f[0]);
			ac.setCount((Long) f[1]);
			return ac;
		}).collect(Collectors.toList()), pageable, repository.count());
	}

	public Page<AccessCount> accessRank(Pageable pageable, Date fromDate, Date toDate) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		final Root<AccessLog> root = query.from(AccessLog.class);

		query.multiselect(root.get(AccessLog_.uri), cb.count(root.get(AccessLog_.uri)))
				.where(cb.greaterThan(root.get(AccessLog_.dateGmt), fromDate),
						cb.lessThan(root.get(AccessLog_.dateGmt), toDate))
				.groupBy(root.get(AccessLog_.uri));

		return new PageImpl<AccessCount>(entityManager.createQuery(query).getResultList().stream().map(f -> {
			AccessCount ac = new AccessCount();
			ac.setUrl((String) f[0]);
			ac.setCount((Long) f[1]);
			return ac;
		}).collect(Collectors.toList()), pageable, repository.count());
	}

}