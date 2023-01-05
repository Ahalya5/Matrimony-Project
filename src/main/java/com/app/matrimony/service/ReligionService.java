package com.app.matrimony.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.entity.Religion;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.ReligionRepository;



@Service
public class ReligionService {
	
	@Autowired
	private ReligionRepository religionRepository;
	
	public void saveOrUpdate(Religion religion) {
		 religionRepository.saveAndFlush(religion);
	}

	public List<Religion> getAll(){
		return religionRepository.findAll();
	}
	
	public Optional<Religion> getById(UUID id){
		return religionRepository.findById(id);
	}
	
	public void deletedById(UUID id) {
		if(id!=null) {
			Optional<Religion> religion=religionRepository.findById(id);
			Religion obj=religion.get();
			obj.setStatus(Status.DELETED);
			obj.setDeletedBy("Ahal");
			obj.setDeletedOn(new Date());
			religionRepository.save(obj);
			
		}
	}
	
}
