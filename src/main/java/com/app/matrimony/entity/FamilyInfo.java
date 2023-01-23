package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.matrimony.enumaration.FamilyMarriedStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "mat_family_info")
public class FamilyInfo extends RecordModifier implements Serializable{

	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUId",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "no_of_brothers")
	private int noOfBrothers;
	
	@Column(name = "no_of_sister")
	private int noOfSister;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "family_married_status")
	private FamilyMarriedStatus familyMarriedStatus;
	
	@Column(name = "parent_phone_no")
	private String parentPhoneNo;
	
	@Column(name = "family_location")
	private String familyLocation;
	
	@Column(name = "father_status")
    private String father_status;	
	
	@Column(name = "mother_status")
    private String mother_status;	
	

	
	
	
}
