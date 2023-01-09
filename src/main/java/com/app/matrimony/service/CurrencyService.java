package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.dto.CurrencyDTO;
import com.app.matrimony.entity.Currency;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.CurrencyRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@Service
@Transactional
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CurrencyService {
	@Autowired
	private @NonNull CurrencyRepository currencyRepository ;
	
	public Currency saveOrUpdate(Currency currency) {
		 return currencyRepository.saveAndFlush(currency);	
	}
	public List<CurrencyDTO> getAllCurrencyDTOs() {
		List<Currency> currencyList = currencyRepository.findAll();
		List<CurrencyDTO> currencyDTOs = new ArrayList<>();
		CurrencyDTO currencyDTO = null;
		for (Currency currrencyInp : currencyList) {
			currencyDTO = new CurrencyDTO();
			currencyDTO.setId(currrencyInp.getId());
			currencyDTO.setName(currrencyInp.getName());
			currencyDTO.setDescription(currrencyInp.getDescription());
			currencyDTOs.add(currencyDTO);
		}
		return currencyDTOs;

	}
	public CurrencyDTO getById(UUID id) {

		Optional<Currency> currencyList = currencyRepository.findById(id);
		Currency currencyDTOs = currencyList.get();
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setId(currencyDTOs.getId());
		currencyDTO.setName(currencyDTOs.getName());
		currencyDTO.setDescription(currencyDTOs.getDescription());
         
		return currencyDTO;

	}

	public List<Currency>getAll(){
		return currencyRepository.findAll();
	}
	public Optional<Currency>getByIds(UUID id){
		return currencyRepository.findById(id);
	}
	
	public void deleteById(UUID id) {
		if (id != null) {
			Optional<Currency> currencyObj = currencyRepository.findById(id);
			Currency currency = currencyObj.get();
			currency.setStatus(Status.DELETED);
			currency.setDeletedBy("vinitha");
			currency.setDeletedOn(new Date());

		}
	}

}
