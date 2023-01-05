package com.app.matrimony.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotherTongueDTO implements Serializable{

	private static  final long serialVersionUID=1L;
	 
	private UUID id;
	
	private String name;
	
	private String description;

}
