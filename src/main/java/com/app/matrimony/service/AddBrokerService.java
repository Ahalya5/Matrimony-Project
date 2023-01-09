package com.app.matrimony.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.entity.Customer;
import com.app.matrimony.entity.User;
import com.app.matrimony.enumaration.Status;
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
	
	public void deleteBroker(UUID id) {
		if (id != null) {
			Optional<AddBroker> UOMObj = addBrokerRepository.findById(id);
			AddBroker obj = UOMObj.get();
			obj.setStatus(Status.INACTIVE);
			obj.setDeletedBy("ebrain");
			obj.setDeletedOn(new Date());
			addBrokerRepository.saveAndFlush(obj);
		}
	}
	
	public Optional<AddBroker> findById(UUID id) {
		return addBrokerRepository.findById(id);
	}
	
	public static String generateRandomPassword(int len) {

		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}


	public AddBroker saveOrUpdate(AddBroker addBroker) {
		return addBrokerRepository.save(addBroker) ;
		
	}

	public List<AddBroker> findAll() {
		return addBrokerRepository.findAll();
	}

	public Optional<AddBroker> getById(UUID id) {
		return addBrokerRepository.findById(id);
	}
	}


