package com.app.matrimony.dto;

import java.util.Date;
import java.util.UUID;

import com.app.matrimony.entity.BasicInfo;
import com.app.matrimony.entity.FamilyInfo;
import com.app.matrimony.entity.HoroscopeDetails;
import com.app.matrimony.entity.LifeStyleInfo;
import com.app.matrimony.entity.ReligionInfo;
import com.app.matrimony.enumaration.Disability;
import com.app.matrimony.enumaration.FamilyStatus;
import com.app.matrimony.enumaration.FamilyType;
import com.app.matrimony.enumaration.FamilyValue;
import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.JobType;
import com.app.matrimony.enumaration.MaritalStatus;
import com.app.matrimony.enumaration.ProfileFor;
import com.app.matrimony.enumaration.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrokerCustomerDTO {
	
	private UUID id;
	private ProfileFor profileFor;
	private String userName ;
	private String name;
	private String phoneNo;
	private Gender gender;
	private Date dateOfBirth;
	private UUID motherTounge;
	private String email;
	private String password;
	private UUID casteId;
	private String subCaste;
	private String gothram;
	private String dosham;
	private UUID religion;
	private MaritalStatus maritalStatus;
	private String height;
	private FamilyStatus familyStatus;
	private FamilyType familyType;
	private FamilyValue familyValue;
	private Disability disability;
	private UUID highestEducation;
	private JobType jobType;
	private RoleType roleType;
	private String occupation;
	private String annualIncome;
	private UUID countryId;
	private UUID stateId;
	private UUID cityId;
	private String about;
	private Boolean isOtpVerified;
	private String otp;
	private Date expiryDate;
	private BasicInfo basicInfoObj;
    private LifeStyleInfo lifeStyleInfoObj;
    private UUID addBrokerId;
    private FamilyInfo familyInfoObj;
    private ReligionInfo religionInfoObj;
    private HoroscopeDetails horoscopeDetailsObj;
	

}
