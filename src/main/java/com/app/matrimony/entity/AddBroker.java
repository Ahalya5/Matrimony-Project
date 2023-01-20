package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.matrimony.enumaration.Status;
import com.app.matrimony.util.PasswordUtil;

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
@Table(name="ma_broker")
public class AddBroker extends RecordModifier implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	public String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address_line1")
	private String addressLine1;
	
	@Column(name = "address_line2")
	private String addressLine2;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Type(type = "uuid-char")
	@Column(name="city")
	private UUID city;
	
	@Type(type = "uuid-char")
	@Column(name="state")
	private UUID state;
	
	@Type(type = "uuid-char")
	@Column(name="country")
	private UUID country;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	public String setAndEncryptPassword(String password) {
		password = PasswordUtil.getEncryptedPassword(password);
		return password;
	}



}
