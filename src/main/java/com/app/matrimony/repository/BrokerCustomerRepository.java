package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.BrokerCustomer;

public interface BrokerCustomerRepository extends WriteableRepository<BrokerCustomer, UUID> {
	
	@Query(value = "Select * from broker_customer bc where bc.phone_no =:phoneNo ",nativeQuery = true)
	public Optional<BrokerCustomer> findByPhoneNo(String phoneNo);
	
	Optional<BrokerCustomer> findByUserName(String name);
	
	@Query(value = "select * from broker_customer bc where bc.email=:email",nativeQuery = true)
	Optional<BrokerCustomer> findByEmail(String email);
	
	@Query(value = "select * from broker_customer bc where bc.role_type =:admin",nativeQuery = true)
	Optional<BrokerCustomer> findByRoleType(String admin);
	
	Optional<BrokerCustomer> findByOtp(String otp);

}
