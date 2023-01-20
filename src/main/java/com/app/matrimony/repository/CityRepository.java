package com.app.matrimony.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.City;

@Repository
public interface CityRepository extends WriteableRepository<City, UUID> {

	@Query(value = "SELECT C From City C WHERE C.stateId =:stateId")
	List<City> findByStateId(UUID stateId);

}
