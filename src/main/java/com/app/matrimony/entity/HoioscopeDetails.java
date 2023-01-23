package com.app.matrimony.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mat_hoiscope_details")
public class HoioscopeDetails extends RecordModifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUId", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id")
	private UUID id;

	
	@Column(name = "time_of_birth")
	private String timeOfbirth;
	
	@Column(name = "place_of_birth")
	private String placeOfBirth;
	
	@Type(type = "uuid-char")
	@Column(name = "state_id")
	private UUID stateId;
	
	@Type(type = "uuid-char")
	@Column(name = "city_id")
	private UUID cityId;
	
	@OneToOne(mappedBy = "hoioscopeDetailsObj")
	private BrokerCustomer obj;
}
