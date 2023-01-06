package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.AddBroker;

public interface AddBrokerRepository extends WriteableRepository<AddBroker, UUID> {
	
	@Query(value ="SELECT PM.* FROM employee_details PM WHERE PM.email=:email and PM.status IN ('ACTIVE','INACTIVE')", nativeQuery = true)	
	Optional<AddBroker> findByEmail(String email);

	

}
