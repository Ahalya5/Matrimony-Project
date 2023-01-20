package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.entity.User;

public interface AddBrokerRepository extends WriteableRepository<AddBroker, UUID> {
	
	@Query(value ="SELECT PM.* FROM ma_broker PM WHERE PM.email=:email and PM.status IN ('ACTIVE','INACTIVE')", nativeQuery = true)	
	Optional<AddBroker> findByEmail(String email);
	
	@Query(value="SELECT * FROM ma_broker u where u.mobile_number =:userName",nativeQuery=true)
	Optional<AddBroker> findByUserName(String userName);
	
	@Query(value="SELECT * FROM ma_broker u WHERE u.mobile_number=:phoneNo",nativeQuery=true)
	Optional<AddBroker> findByMobileNo(String phoneNo);

	@Query(value="SELECT * FROM ma_broker u WHERE u.email= :email",nativeQuery=true)
	Optional<AddBroker> findByUserEmail(String email);




	

}
