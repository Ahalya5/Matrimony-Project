package com.app.matrimony.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.StandardException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfoDTO {
	private UUID id;
	private String bodyType;
	private String weight;
	private String clgInstitution;
	private String organization;

}
