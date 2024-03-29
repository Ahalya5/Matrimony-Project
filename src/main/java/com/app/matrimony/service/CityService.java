package com.app.matrimony.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.entity.City;
import com.app.matrimony.repository.CityRepository;

@Service

public class CityService {
	@Autowired
	private CityRepository cityRepository;

	public List<City> findAllCity() {

		return cityRepository.findAll();
	}

	public Optional<City> findById(UUID id) {
		return cityRepository.findById(id);
	}

	public List<City> findByStateId(UUID stateId) {
		return cityRepository.findByStateId(stateId);
	}

}
