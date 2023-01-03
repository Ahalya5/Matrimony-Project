package com.app.matrimony.dto;

import java.util.Date;
import java.util.UUID;

import com.app.matrimony.enumaration.Disability;
import com.app.matrimony.enumaration.FamilyStatus;
import com.app.matrimony.enumaration.FamilyType;
import com.app.matrimony.enumaration.FamilyValue;
import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.JobType;
import com.app.matrimony.enumaration.MaritalStatus;
import com.app.matrimony.enumaration.ProfileFor;

import groovyjarjarpicocli.CommandLine.Help.Ansi.Text;
import lombok.Data;

@Data
public class CustomerDto {
	
	private UUID id;
	private ProfileFor profileFor;
	private String name ;
	private String phoneNo;
	private Gender gender;
	private Date dateOfBirth;
	private UUID motherTounge;
	private String email;
	private String password;
	private UUID caste;
	private Text gothram;
	private String dosham;
	private MaritalStatus maritalStatus;
	private String height;
	private FamilyStatus familyStatus;
	private FamilyType familyType;
	private FamilyValue familyValue;
	private Disability disability;
	private UUID heigherEducation;
	private JobType jobType;
	private Text occupation;
	private String annualIncome;
	private UUID country;
	private UUID state;
	private UUID city;
	private Text about;

}
