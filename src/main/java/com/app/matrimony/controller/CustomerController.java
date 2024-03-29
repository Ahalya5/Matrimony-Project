package com.app.matrimony.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.CustomerDto;
import com.app.matrimony.entity.Customer;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.CustomerService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.validation.CustomerValidator;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/employee")
@Api(value = "Customer Rest API", produces = "application/json", consumes = "application/json")
public class CustomerController {
	
	private @NonNull ResponseGenerator responseGenerator;
	private @NonNull MessagePropertyService messageSource;
	private @NonNull CustomerService customerService;
	private @NonNull CustomerValidator validatorService;
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	@ApiOperation(value = "Allows to create employeeDetails.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The employee request payload") @RequestBody CustomerDto request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);
		customerService.saveOrUpdate((Customer)(validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("employee.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
