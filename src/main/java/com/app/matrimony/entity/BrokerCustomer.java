package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
import com.app.matrimony.enumaration.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "mat_broker_customer")
public class BrokerCustomer extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id",nullable = false, updatable = false)
	private UUID id;

	@NotNull(message = "profileFor cannot be blank")
	@Enumerated(EnumType.STRING)
	@Column(name = "profile_for")
	private ProfileFor profileFor;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_no")
	private String phoneNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	public Gender gender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Type(type = "uuid-char")
	@Column(name = "religion")
	private UUID religion;

	@Type(type = "uuid-char")
	@Column(name = "mother_tongue")
	private UUID motherTounge;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Type(type = "uuid-char")
	@Column(name = "caste_id")
	private UUID casteId;

	@Column(name = "sub_caste")
	private String subCaste;

	@Column(name = "gothram")
	private String gothram;

	@Column(name = "dosham")
	private String dosham;

	@Enumerated(EnumType.STRING)
	@Column(name = "marital_status")
	public MaritalStatus maritalStatus;

	@Column(name = "height")
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

	@Type(type = "uuid-char")
	@Column(name = "highest_education")
	private UUID highestEducation;

	@Enumerated(EnumType.STRING)
	@Column(name = "job_type")
	public JobType jobType;

	@Column(name = "occupation")
	private String occupation;

	@Column(name = "annual_income")
	private String annualIncome;

	@Type(type = "uuid-char")
	@Column(name = "country_id")
	private UUID countryId;

	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;

	@Type(type = "uuid-char")
	@Column(name = "city_id")
	private UUID cityId;

	@Column(name = "about")
	private String about;

	@Column(name = "otp_verified")
	private Boolean isOtpVerified;

	@Column(name = "otp")
	private String otp;

	@Column(name = "expiry_date")
	private Date expiryDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_type")
	private RoleType roleType;
	
	@Column(name = "add_broker_id")
	 private UUID addBrokerId;

	public void setAndEncryptPassword(String password) {
		setPassword(PasswordUtils.getEncryptedPassword(password));
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "basic_info_id")
	private BasicInfo basicInfoObj;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "life_style_info_id")
	private LifeStyleInfo lifeStyleInfoObj;

}
