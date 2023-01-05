package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.dto.EducationDTO;
import com.app.matrimony.entity.Education;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.EducationRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })

public class EducationService {

	private @NonNull EducationRepository educationRepository;

	public void saveOrUpdate(Education education) {
		educationRepository.saveAndFlush(education);
	}

	public List<EducationDTO> getAll() {
		List<Education> educationList = educationRepository.findAll();
		List<EducationDTO> educationDTOList = new ArrayList<>();
		EducationDTO educationDTO = null;
		for (Education educationInp : educationList) {
			educationDTO = new EducationDTO();
			educationDTO.setId(educationInp.getId());
			educationDTO.setName(educationInp.getName());
			educationDTO.setDescription(educationInp.getDescription());
			educationDTOList.add(educationDTO);

		}
		return educationDTOList;
	}

	public EducationDTO getById(UUID id) {
		Optional<Education> educationOptional = educationRepository.findById(id);
		Education educationInp = educationOptional.get();
		EducationDTO educationDTO = new EducationDTO();
		educationDTO.setId(educationInp.getId());
		educationDTO.setName(educationInp.getName());
		educationDTO.setDescription(educationInp.getDescription());

		return educationDTO;
	}

	public Optional<Education> findById(UUID id) {
		return educationRepository.findById(id);
	}

	public void delete(UUID id) {
		if (id != null) {
			Optional<Education> educationobj = educationRepository.findById(id);
			Education obj = educationobj.get();
			obj.setStatus(Status.DELETED);
			obj.setDeletedBy("ebrain");
			obj.setDeletedOn(new Date());
			educationRepository.saveAndFlush(obj);
		}
	}
}
