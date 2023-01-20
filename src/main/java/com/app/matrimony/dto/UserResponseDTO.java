package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.Status;

public interface UserResponseDTO {
	
	UUID getId();

	String getUserName();

	String getPassword();

	String getEmail();

	String getFirstName();

	String getLastName();

	String getPhoneNo();

	String getAlternatePhoneNo();

	String getAddressLine1();

	String getAddressLine2();

	Gender getGender();

	UUID getCityId();

	UUID getStateId();

	UUID getCountryId();

	Status getStatus();

	String getNote();

	UUID getRoleId();

	String getRoleName();


}
