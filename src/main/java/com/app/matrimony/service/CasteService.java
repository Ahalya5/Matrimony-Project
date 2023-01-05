package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.dto.CasteDTO;
import com.app.matrimony.entity.Caste;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.CasteRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CasteService {
	@Autowired
	private @NonNull CasteRepository casteRepository;

	public void saveOrUpdate(Caste caste) {
		casteRepository.saveAndFlush(caste);
	}

	public List<CasteDTO> getAll() {
		List<Caste> casteList = casteRepository.findAll();
		List<CasteDTO> casteDTOList = new ArrayList<>();
		CasteDTO casteDTO = null;
		for (Caste casteInp : casteList) {
			casteDTO = new CasteDTO();
			casteDTO.setId(casteInp.getId());
			casteDTO.setCasteName(casteInp.getCasteName());
			casteDTO.setDescription(casteInp.getDescription());
			casteDTOList.add(casteDTO);

		}
		return casteDTOList;
	}

	public CasteDTO getById(UUID id) {
		Optional<Caste> casteOptional = casteRepository.findById(id);
		Caste casteInp = casteOptional.get();
		CasteDTO casteDTO = new CasteDTO();
		casteDTO.setId(casteInp.getId());
		casteDTO.setCasteName(casteInp.getCasteName());
		casteDTO.setDescription(casteInp.getDescription());

		return casteDTO;
	}

	public Optional<Caste> findById(UUID id) {
		return casteRepository.findById(id);
	}

	public void delete(UUID id) {
		if (id != null) {
			Optional<Caste> casteobj = casteRepository.findById(id);
			Caste obj = casteobj.get();
			obj.setStatus(Status.DELETED);
			obj.setDeletedBy("ebrain");
			obj.setDeletedOn(new Date());
			casteRepository.saveAndFlush(obj);
		}
	}
}
