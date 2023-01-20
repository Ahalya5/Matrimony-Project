package com.app.matrimony.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.ErrorDto;
import com.app.matrimony.dto.LoginRequest;
import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.repository.AddBrokerRepository;
import com.app.matrimony.repository.UserRepository;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.security.JwtTokenUtil;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.PasswordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "Authorization Rest API", description = "Broker Login")
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class BrokerAuthenticationController {
	
private static final Logger logger = Logger.getLogger(AuthenticationController.class);
	
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull UserRepository userRepository;

	private @NonNull AddBrokerRepository addBrokerRepository;
	
	private @NonNull JwtTokenUtil jwtTokenUtil;

	private @NonNull MessageSource messageSource;

	private @NonNull MessagePropertyService messagePropertySource;

	private @NonNull ApplicationEventPublisher applicationEventPublisher;
	
	
	@ApiOperation(value = "Logs the user in to the system and return the auth tokens")
	@RequestMapping(value = "/loginBroker", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@ApiParam(value = "The LoginRequest payload") @RequestBody LoginRequest request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (null == request) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid Request Payload.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Optional<AddBroker> brokerOptional = addBrokerRepository.findByUserName(request.getUserName());
		if (!brokerOptional.isPresent()) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid Username.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		AddBroker addBroker = brokerOptional.get();

		String encryptedPassword = PasswordUtil.getEncryptedPassword(request.getPassword());
		if (!addBroker.getPassword().equals(encryptedPassword)) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Password is wrong.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		final String token = jwtTokenUtil.generateToken(addBroker);
		response.put("status", 1);
		response.put("role", addBroker.getId());
		response.put("message", "Logged in Successfully.!");
		response.put("jwt", token);
		response.put("isOtpVerified", true);
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	
	


}
