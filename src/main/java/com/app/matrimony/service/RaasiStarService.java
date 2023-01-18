package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.dto.RaasiStarDto;
import com.app.matrimony.entity.RaasiStar;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.RaasiStarRepository;

@Service
public class RaasiStarService {

	@Autowired
	private RaasiStarRepository raasiStarRepository;

	public RaasiStar saveOrUpdate(RaasiStar raasiStar) {
		return raasiStarRepository.saveAndFlush(raasiStar);
	}

	public List<RaasiStarDto> getAllRStar() {
		List<RaasiStar> raasiStarList = raasiStarRepository.findAll();
		List<RaasiStarDto> raasiStarrDTOList = new ArrayList<>();
		RaasiStarDto rStarDto = null;
		for (RaasiStar rStarInp : raasiStarList) {
			rStarDto = new RaasiStarDto();
			rStarDto.setId(rStarInp.getId());
			rStarDto.setName(rStarInp.getName());
			rStarDto.setDescription(rStarInp.getDescription());
			rStarDto.setStar(rStarInp.getStar());
			raasiStarrDTOList.add(rStarDto);
		}
		return raasiStarrDTOList;

	}

	public List<RaasiStar> getActives() {
		return raasiStarRepository.getActiveRaasi();
	}

	public RaasiStarDto getRStarById(UUID id) {

		Optional<RaasiStar> rStarList = raasiStarRepository.findById(id);
		RaasiStarDto rStarDto = new RaasiStarDto();
		RaasiStar starInp = rStarList.get();
		rStarDto.setId(starInp.getId());
		rStarDto.setName(starInp.getName());
		rStarDto.setDescription(starInp.getDescription());
		rStarDto.setStatus(starInp.getStatus());
		rStarDto.setStar(starInp.getStar());

		return rStarDto;

	}

	public Optional<RaasiStar> getRaasiById(UUID id) {
		return raasiStarRepository.findById(id);

	}

	public void deleteById(UUID id) {
		if (id != null) {
			Optional<RaasiStar> raasiStarObj = raasiStarRepository.findById(id);
			RaasiStar raasiStar = raasiStarObj.get();
			raasiStar.setStatus(Status.DELETED);
			raasiStar.setDeletedBy("Hacker");
			raasiStar.setDeletedOn(new Date());

			raasiStarRepository.saveAndFlush(raasiStar);

		}

	}

}
