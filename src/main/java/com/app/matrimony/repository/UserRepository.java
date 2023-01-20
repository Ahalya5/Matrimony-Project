package com.app.matrimony.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.dto.UserResponseDTO;
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
	
	@Query(value = "SELECT u.user_id as userId,r.role_name as roleName, u.user_name as userName,u.password as password,u.email_id as email,"
			+ " u.first_name as firstName, u.last_name as lastName, u.mobile_no as phoneNo, u.alternate_mobile_no as alternatePhoneNo,"
			+ " u.address_line1 as addressLine1, u.address_line2 as addressLine2, u.gender as gender, u.city_id as cityId,"
			+ " u.state_id as stateId, u.country_id as countryId,u.role_id as roleId, u.status as status, u.note as note from ma_user u inner join ma_role r on u.role_id=r.id and u.status=:status", nativeQuery = true)
	List<UserResponseDTO> findByUserRoleType(String status);

}

