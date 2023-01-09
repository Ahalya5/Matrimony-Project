package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.enumaration.Status;

import lombok.Data;
@Data
public class AddBrokerDTO {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String addressLine1;
	private String addressLine2;
	private String email;
	private String mobileNumber;
	private UUID state;
	private UUID city;
	private UUID country;
	private Status status;

	
	








	
	

}
