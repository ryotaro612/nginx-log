package org.ranceworks.nginxlog.repositories;

import java.util.List;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface AccessLogRepository extends Repository<AccessLog, Long> {// ,
																			// JpaSpecificationExecutor<AccessLog>
																			// {

	public List<AccessLog> findByCountryCode(String countryCode);

	public long count();
}
