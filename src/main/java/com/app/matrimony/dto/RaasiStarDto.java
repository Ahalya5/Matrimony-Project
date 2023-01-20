package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.entity.RecordModifier;

import lombok.Data;

@Data
public class RaasiStarDto extends RecordModifier{
	
	private UUID id;
	private String name;
	private String description;
	private UUID star;

}
