package com.app.matrimony.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HoioscopeDetailsDTO {
	private UUID id;
	private String timeOfbirth;
	private String placeOfBirth;
	private UUID stateId;
	private UUID cityId;

}
