package com.app.matrimony.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.entity.Customer;
import com.app.matrimony.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	public Customer saveOrUpdate(Customer customer) {
		return customerRepository.save(customer) ;
		
	}
	public Optional<Customer> findByEmail (String email){
		return customerRepository.findByEmail(email);
	}
	
	public Optional<Customer> findById(UUID id) {
		return customerRepository.findById(id);
	}

}
