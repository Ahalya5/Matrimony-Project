package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.enumaration.Drinking;
import com.app.matrimony.enumaration.EatingHabit;
import com.app.matrimony.enumaration.Smoking;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LifeStyleInfoDTO {
	
	private UUID id;
	private EatingHabit eatingHabit;
	private Drinking drinking;
	private Smoking smoking;

}
