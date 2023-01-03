package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.matrimony.enumaration.Disability;
import com.app.matrimony.enumaration.FamilyStatus;
import com.app.matrimony.enumaration.FamilyType;
import com.app.matrimony.enumaration.FamilyValue;
import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.JobType;
import com.app.matrimony.enumaration.MaritalStatus;
import com.app.matrimony.enumaration.ProfileFor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name="ma_customer")
public class Customer extends RecordModifier implements Serializable {
	
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotNull(message = "profileFor cannot be blank")
	@Enumerated(EnumType.STRING)
	@Column(name = "profile_for")
	private ProfileFor profileFor;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone_no")
	private String phoneNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	public Gender gender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="religion")
	private UUID religion;
	
	@Column(name="mother_tongue")
	private UUID motherTounge;
	
	@Column(name = "email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="caste")
	private UUID caste;
	
	@Column(name="sub_caste")
	private String subCaste;
	
	@Column(name="gothram")
	private String gothram;
	
	@Column(name="dosham")
	private String dosham;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "marital_status")
	public MaritalStatus maritalStatus;
	
	@Column(name="height")
	private String height;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "family_status")
	public FamilyStatus familyStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "family_Type")
	public FamilyType familyType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "family_value")
	public FamilyValue familyValue;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "disability")
	public Disability disability;
	
	@Column(name="heigher_education")
	private UUID heigherEducation;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "job_type")
	public JobType jobType;
	
	@Column(name="occupation")
	private String occupation;
	
	@Column(name="annual_income")
	private String annualIncome;
	
	@Column(name="country")
	private UUID country;
	
	@Column(name="state")
	private UUID state;
	
	@Column(name="city")
	private UUID city;
	
	@Column(name="about")
	private String about;
	

}
