package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.entity.User;

public interface AddBrokerRepository extends WriteableRepository<AddBroker, UUID> {
	
	@Query(value ="SELECT PM.* FROM employee_details PM WHERE PM.email=:email and PM.status IN ('ACTIVE','INACTIVE')", nativeQuery = true)	
	Optional<AddBroker> findByEmail(String email);
	
	@Query(value="SELECT * FROM ma_user u where u.email_id =:userName",nativeQuery=true)
	Optional<AddBroker> findByUserName(String userName);
	
	@Query(value="SELECT * FROM ma_user u WHERE u.mobile_no=:phoneNo",nativeQuery=true)
	Optional<AddBroker> findByMobileNo(String phoneNo);

	@Query(value="SELECT * FROM ma_user u WHERE u.email_id= :email",nativeQuery=true)
	Optional<AddBroker> findByUserEmail(String email);




	

}
