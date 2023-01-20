package com.app.matrimony.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.entity.State;
import com.app.matrimony.repository.StateRepository;

@Service
public class StateService {
	@Autowired
	private StateRepository stateRepository;

	public List<State> findAllState() {

		return stateRepository.findAll();
	}

	public List<State> findByCountryId(UUID countryId) {
		return stateRepository.findByCountryId(countryId);
	}

}
