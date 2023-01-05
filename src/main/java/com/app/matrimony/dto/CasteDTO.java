package com.app.matrimony.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CasteDTO {
	
	private UUID id;
	private String casteName;
	private String description;

}
