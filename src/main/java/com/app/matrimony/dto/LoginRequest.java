package com.app.matrimony.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
}
