package com.app.matrimony.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ValidatorOtpDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID BrokerCustomerId;
	private String otp;
	private String phoneNo;
}
