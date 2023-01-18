package com.app.matrimony.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.RaasiStar;

public interface RaasiStarRepository extends WriteableRepository<RaasiStar, UUID> {

	@Query(value = "SELECT * From mat_raasi_star as rs WHERE rs.status ='ACTIVE'", nativeQuery = true)
	List<RaasiStar> getActiveRaasi();

}
