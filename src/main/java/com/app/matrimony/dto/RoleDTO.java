package com.app.matrimony.dto;

import java.io.Serializable;
import java.util.UUID;

import com.app.matrimony.entity.RecordModifier;

import lombok.Data;

@Data
public class RoleDTO extends RecordModifier implements Serializable {
		
	private UUID id;
	private String roleName;
	private String description;

	
	 
}