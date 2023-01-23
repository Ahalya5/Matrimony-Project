package com.app.matrimony.dto;

import java.util.UUID;

import com.app.matrimony.entity.BrokerCustomer;
import com.app.matrimony.enumaration.FamilyMarriedStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyinfoDTO {
	private UUID id;
	private int noOfBrothers;
	private int noOfSister;
	private FamilyMarriedStatus familyMarriedStatus;
	private String parentPhoneNo;
	private String familyLocation;
	private String father_status;
	private String masther_status;


}
