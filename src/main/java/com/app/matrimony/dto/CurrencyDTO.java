package com.app.matrimony.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CurrencyDTO {
	private UUID id;
	private String name;
	private String description;

}
