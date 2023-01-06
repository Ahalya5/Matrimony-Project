package com.app.matrimony.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.entity.Customer;
import com.app.matrimony.repository.AddBrokerRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AddBrokerService {
	
	@Autowired
	AddBrokerRepository addBrokerRepository;

	public Optional<AddBroker> findByEmail(String email) {
		return addBrokerRepository.findByEmail(email);
		
	}
	
	public Optional<AddBroker> findById(UUID id) {
		return addBrokerRepository.findById(id);
	}



	public AddBroker saveOrUpdate(AddBroker addBroker) {
		return addBrokerRepository.save(addBroker) ;
		
	}

}
