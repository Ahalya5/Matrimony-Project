package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.dto.AnnualIncomeDTO;
import com.app.matrimony.entity.AnnualIncome;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.AnnualIncomeRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AnnualIncomeService {
	@Autowired
	private @NonNull AnnualIncomeRepository annualIncomeRepository;
	
	public AnnualIncome saveOrUpdate(AnnualIncome annualIncome) {
		return annualIncomeRepository.saveAndFlush(annualIncome);
	}
	
	public List<AnnualIncomeDTO> getAllAnnualIncomeDTOs() {
		List<AnnualIncome> annualIncomeList = annualIncomeRepository.findAll();
		List<AnnualIncomeDTO> annualIncomeDTOs = new ArrayList<>();
		AnnualIncomeDTO annualIncomeDTO = null;
		for (AnnualIncome annualIncomeInp : annualIncomeList) {
			annualIncomeDTO = new AnnualIncomeDTO();
			annualIncomeDTO.setId(annualIncomeInp.getId());
			annualIncomeDTO.setName(annualIncomeInp.getName());
			annualIncomeDTO.setDescription(annualIncomeInp.getDescription());
			annualIncomeDTOs.add(annualIncomeDTO);
		}
		return annualIncomeDTOs;
	}
	
	
	public AnnualIncomeDTO getById(UUID id) {
		Optional<AnnualIncome> annualIncomeList = annualIncomeRepository.findById(id);
		AnnualIncome annualIncomeInp = annualIncomeList.get();
		AnnualIncomeDTO annualIncomeDTO = new AnnualIncomeDTO();
		annualIncomeDTO.setId(annualIncomeInp.getId());
		annualIncomeDTO.setName(annualIncomeInp.getName());
		annualIncomeDTO.setDescription(annualIncomeInp.getDescription());

		return annualIncomeDTO;

	}


	public List<AnnualIncome>getAll(){
		return annualIncomeRepository.findAll();
	}
	public Optional<AnnualIncome>getByIds(UUID id){
		return annualIncomeRepository.findById(id);
	}
	public void deleteById(UUID id) {
		if(id != null) {
			Optional<AnnualIncome>annualIncome = annualIncomeRepository.findById(id);
			AnnualIncome obj = annualIncome.get();
			obj.setDeletedBy("vinitha");
			obj.setDeletedOn(new Date());
			obj.setStatus(Status.DELETED);
			annualIncomeRepository.saveAndFlush(obj);
}
	}
}
