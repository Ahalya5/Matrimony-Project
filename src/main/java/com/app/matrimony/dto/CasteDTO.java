package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.entity.RecordModifier;

import lombok.Data;

@Data
public class CasteDTO extends RecordModifier {
	
	private UUID id;
	private String casteName;
	private String description;

}
