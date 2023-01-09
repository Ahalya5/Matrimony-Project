package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.matrimony.enumaration.Gender;
import com.app.matrimony.enumaration.RoleType;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.util.PasswordUtil;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ma_user")
public class User extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "user_id", updatable = false, nullable = false)
	private UUID userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	public String password;

	@Column(name = "email_id")
	public String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "alternate_mobile_no")
	private String alternatePhoneNo;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@Type(type = "uuid-char")
	@Column(name = "city_id")
	private UUID cityId;

	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;

	@Type(type = "uuid-char")
	@Column(name = "country_id")
	private UUID countryId;

	@Type(type = "uuid-char")
	@Column(name = "role_id")
	private UUID roleId;

	@Column(name = "note")
	private String note;

	public String setAndEncryptPassword(String password) {
		password = PasswordUtil.getEncryptedPassword(password);
		return password;
	}

}