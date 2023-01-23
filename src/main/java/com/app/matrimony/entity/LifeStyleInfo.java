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

import com.app.matrimony.enumaration.Drinking;
import com.app.matrimony.enumaration.EatingHabit;
import com.app.matrimony.enumaration.Smoking;

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
@Table(name = "mat_life_style_info")
public class LifeStyleInfo extends RecordModifier implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id",nullable = false, updatable = false)
	private UUID id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "smoking")
	private Smoking smoking;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "eating_habit")
	private EatingHabit eatingHabit;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drinking")
	private Drinking drinking;
	


}
