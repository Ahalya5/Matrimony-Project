package com.app.matrimony.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	@Entity
	@Table(name = "mat_country")
	public class Country implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(generator = "UUID")
		@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
		@Type(type = "uuid-char")
		@Column(name = "id", updatable = false, nullable = false)
		private UUID id;

		@Column(name = "country_name")
		private String countryName;

		@Column(name = "short_desc")
		private String shortName;

}
