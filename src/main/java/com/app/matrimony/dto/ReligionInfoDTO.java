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
public class ReligionInfoDTO {
	private UUID id;
	private UUID starId;
	private UUID raasiId;

}
