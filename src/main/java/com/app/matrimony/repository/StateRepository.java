package com.app.matrimony.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.State;

@Repository
public interface StateRepository extends WriteableRepository<State, UUID> {
	@Query(value = "SELECT S From State S WHERE S.countryId =:countryId")
	List<State> findByCountryId(UUID countryId);

}
