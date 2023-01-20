package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.matrimony.dto.RoleDTO;
import com.app.matrimony.entity.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Optional<RoleDTO> findByRoleName(String roleName);
	
	Optional<Role> findByRoleNameAndIdNot(String roleName, UUID Id);

	void saveAndFlush(RoleDTO role);

	

}