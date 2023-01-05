package com.app.matrimony.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.entity.MotherTongue;
import com.app.matrimony.entity.Religion;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.MotherTongueRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class MotherTongueService {
	
	@Autowired
	private MotherTongueRepository motherTongueRepository;
	
	public void saveOrUpdate(MotherTongue motherTongue) {
		motherTongueRepository.saveAndFlush(motherTongue);
	}
	public List<MotherTongue> getAll(){
		return motherTongueRepository.findAll();
	}
	
	public Optional<MotherTongue> getById(UUID id){
		return motherTongueRepository.findById(id);
	}
	
	public void deletedById(UUID id) {
		if(id!=null) {
			Optional<MotherTongue> mothertongue=motherTongueRepository.findById(id);
			MotherTongue obj=mothertongue.get();
			obj.setStatus(Status.DELETED);
			obj.setDeletedBy("Ahal");
			obj.setDeletedOn(new Date());
			motherTongueRepository.save(obj);
			
		}
	}

}
