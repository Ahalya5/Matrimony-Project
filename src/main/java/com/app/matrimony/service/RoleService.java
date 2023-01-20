package com.app.matrimony.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.dto.RoleDTO;
import com.app.matrimony.entity.Role;
import com.app.matrimony.repository.RoleRepository;


@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository repository;

	public void saveorUpdate(RoleDTO request) {
		repository.saveAndFlush(request);

	}

	public Optional<Role> findById(UUID id) {
		return repository.findById(id);
	}

	public List<Role> findAll() {
		return repository.findAll();
	}

	public void deleteById(UUID id) {
		repository.deleteById(id);
	}

	public void saveorUpdate(Role obj) {
		repository.saveAndFlush(obj);
	}
}