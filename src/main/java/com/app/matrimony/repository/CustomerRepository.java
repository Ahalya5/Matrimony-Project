package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.Customer;

public interface CustomerRepository extends WriteableRepository<Customer, UUID> {
	
	@Query(value ="SELECT PM.* FROM employee_details PM WHERE PM.email=:email and PM.status IN ('ACTIVE','INACTIVE')", nativeQuery = true)	
	Optional<Customer> findByEmail(String email);
	
	

}
