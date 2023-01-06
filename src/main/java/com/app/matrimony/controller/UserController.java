package com.app.matrimony.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.UserDTO;
import com.app.matrimony.entity.User;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.UserService;
import com.app.matrimony.validation.UserValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins ="*",maxAge=3600)
@RequestMapping("/api/user")
@Api(value="User Registration Rest API",produces="application/json",consumes="application/json")
@RestController
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private ResponseGenerator responseGenerator;
	@Autowired
	private MessagePropertyService messageSource;
	@Autowired
	private UserService userservice;
	@Autowired
	private UserValidation validatorService;
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "Allows to create user.", response = Response.class)
	@PostMapping(value = "/registration", produces = "application/json")
	public ResponseEntity<?> userRegistration(
			@ApiParam(value = "The user registration request payload") @RequestBody UserDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);

		try {
			userservice.saveOrUpdate((User) (validationResult.getObject()));
			return responseGenerator.successResponse(context, messageSource.getMessage("user.register"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to fetch User list.", response = Response.class)
	@GetMapping(value = "/getuserlist", produces = "application/json")
	public ResponseEntity<?> getUserList(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			
		List<User> user = userservice.findAll();

			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.get"),user,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch particular Role.", response = Response.class)
	@GetMapping(value = "/getuser/{id}", produces = "application/json")
	public ResponseEntity<?> getUserById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<User> user = userservice.getById(id);

			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.get.by.id"), user,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateuser")
	public ResponseEntity<?> updateUser(@ApiParam(value = "The Update request payload") @RequestBody UserDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = validatorService.validate(RequestType.PUT, request);

		try {
			userservice.saveOrUpdate((User) validationResult.getObject());
			return responseGenerator.successResponse(context, messageSource.getMessage("user.update"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to update existing User.", response = Response.class)
	@PutMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		userservice.deleteUser(id);
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("user.delete"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
