package com.app.matrimony.dto;

import java.io.Serializable;
import java.util.UUID;

import com.app.matrimony.entity.RecordModifier;
import com.app.matrimony.entity.Role;
import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.RoleType;

import lombok.Data;

@Data
public class UserDTO extends RecordModifier implements Serializable { 

	private UUID userId;
	private String userName;
	public String password;
	public String email;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String alternatePhoneNo;
	private String addressLine1;
	private String addressLine2;
	private UUID roleId;
	private Gender gender;
	private UUID cityId;
	private UUID stateId;
	private UUID countryId;
	private String note;
	
}