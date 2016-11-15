package org.ranceworks.nginxlog.repositories;

import java.util.List;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface AccessLogRepository extends Repository<AccessLog, Long>, JpaSpecificationExecutor<AccessLog> {

	public List<AccessLog> findByCountryCode(String countryCode);

	public List<AccessLog> findAll(Specification<AccessLog> spec);

	public List<AccessLog> findAll();

	public Page<AccessLog> findAll(Pageable pagealbe);

	public long count();
}
