package org.ranceworks.nginxlog.services;

import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RankService {
	@Autowired
	private EntityManager entityManager;

	private long countDistinctUri() {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> query = cb.createQuery(Long.class);
		final Root<AccessLog> root = query.from(AccessLog.class);
		query.from(AccessLog.class);
		query.distinct(true);
		return entityManager.createQuery(query.select(cb.countDistinct(root.get(AccessLog_.uri)))).getSingleResult();
	}

	public Page<AccessCount> accessRank(Pageable pageable, Date date) {
		return accessRank(pageable, Optional.of(date), Optional.of(date));
	}

	public Page<AccessCount> accessRank(Pageable pageable, Optional<Date> fromDate, Optional<Date> toDate) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		final Root<AccessLog> root = query.from(AccessLog.class);

		final Predicate[] p = java.util.Arrays
				.asList(fromDate.map(d -> cb.greaterThanOrEqualTo(root.get(AccessLog_.dateGmt), d)),
						toDate.map(d -> cb.lessThanOrEqualTo(root.get(AccessLog_.dateGmt), d)))
				.stream().filter(f -> f.isPresent()).map(Optional::get).collect(Collectors.toList())
				.toArray(new Predicate[0]);

		query.multiselect(root.get(AccessLog_.uri), cb.count(root.get(AccessLog_.uri))).where(p)
				.groupBy(root.get(AccessLog_.uri));
		return new PageImpl<AccessCount>(entityManager.createQuery(query).setFirstResult(pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList().stream().map(f -> {
					AccessCount ac = new AccessCount();
					ac.setUrl((String) f[0]);
					ac.setCount((Long) f[1]);
					return ac;
				}).collect(Collectors.toList()), pageable, countDistinctUri());
	}

}