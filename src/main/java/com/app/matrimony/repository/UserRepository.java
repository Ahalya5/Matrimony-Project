package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.User;

public interface UserRepository extends WriteableRepository<User, UUID> {

	@Query(value="SELECT * FROM ma_user u where u.email_id =:userName",nativeQuery=true)
	Optional<User> findByUserName(String userName);

	@Query(value = "SELECT * FROM ma_user u where u.email =:email",nativeQuery=true)
	Optional<User> findByEmail(String email);

	@Query(value="SELECT * FROM ma_user u WHERE u.mobile_no=:phoneNo",nativeQuery=true)
	Optional<User> findByMobileNo(String phoneNo);

	@Query(value="SELECT * FROM ma_user u WHERE u.email_id= :email",nativeQuery=true)
	Optional<User> findByUserEmail(String email);

}

