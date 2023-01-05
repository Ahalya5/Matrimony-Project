package com.app.matrimony.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.dto.StarDto;
import com.app.matrimony.entity.Star;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.StarRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class StarService {

	private @NonNull StarRepository starRepository;

	public Star saveOrUpdate(Star star) {
		return starRepository.saveAndFlush(star);
	}

	public List<StarDto> getAllStar() {
		List<Star> starList = starRepository.findAll();
		List<StarDto> starrDTOList = new ArrayList<>();
		StarDto starDto = null;
		for (Star starInp : starList) {
			starDto = new StarDto();
			starDto.setId(starInp.getId());
			starDto.setName(starInp.getName());
			starDto.setDescription(starInp.getDescription());
			starrDTOList.add(starDto);
		}
		return starrDTOList;

	}

	public StarDto getStarById(UUID id) {

		Optional<Star> starList = starRepository.findById(id);
		StarDto starDto = new StarDto();
		Star starInp = starList.get();
		starDto.setId(starInp.getId());
		starDto.setName(starInp.getName());
		starDto.setDescription(starInp.getDescription());

		return starDto;

	}

	public Optional<Star> getById(UUID id) {
		return starRepository.findById(id);
	}

	public void deleteById(UUID id) {
		if (id != null) {
			Optional<Star> starObj = starRepository.findById(id);
			Star star = starObj.get();
			star.setStatus(Status.DELETED);
			star.setDeletedBy("Hacker");
			star.setDeletedOn(new Date());
			starRepository.saveAndFlush(star);

		}

	}

}
