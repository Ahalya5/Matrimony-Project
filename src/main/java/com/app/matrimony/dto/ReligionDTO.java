package com.app.matrimony.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReligionDTO implements Serializable {
	
	private static  final long serialVersionUID=1L;
	 
	private UUID id;
	
	private String name;
	
	private String description;

}
