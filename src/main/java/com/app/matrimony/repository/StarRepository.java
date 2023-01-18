package com.app.matrimony.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.Star;

@Repository
public interface StarRepository extends WriteableRepository<Star, UUID> {

	@Query(value = "SELECT * From mat_star as ms WHERE ms.status ='ACTIVE'", nativeQuery = true)
	List<Star> getActiveStars();

}
