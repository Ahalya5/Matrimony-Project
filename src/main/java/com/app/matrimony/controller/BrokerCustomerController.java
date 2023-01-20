package com.app.matrimony.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.BrokerCustomerDTO;
import com.app.matrimony.dto.ErrorDto;
import com.app.matrimony.dto.LoginRequest;
import com.app.matrimony.dto.ValidatorOtpDTO;
import com.app.matrimony.entity.BrokerCustomer;
import com.app.matrimony.entity.PasswordUtils;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.BrokerCustomerRepository;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.security.JwtTokenUtil;
import com.app.matrimony.service.BrokerCustomerService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.validation.BrokerCustomerValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(value = "/api/user/brokerCustomer")
@Api(value = "Api the broker customer long in request",produces = "application/json",consumes  = "application/json")
public class BrokerCustomerController {
	
	private static final Logger logger=Logger.getLogger(BrokerCustomerController.class);
	
	@Autowired
	private ResponseGenerator responseGenerator;
	
	@Autowired
	private BrokerCustomerService brokerCustomerService;
	
	@Autowired
	private MessagePropertyService messagePropertyService;
	
	@Autowired
	private BrokerCustomerValidation validation;
	
	@Autowired 
	private BrokerCustomerRepository brokerCustomerRepository;
	
	@Autowired 
	private JwtTokenUtil jwtTokenUtil;
	
	@ApiOperation(value = "Allow the create Api",response = Response.class)
	@PostMapping(value = "/create",produces = "application/json",consumes = "application/json")
	ResponseEntity<?> create(@ApiParam(value = "brokerCustomer allow the response")@RequestBody BrokerCustomerDTO request,
	@RequestHeader HttpHeaders httpHeaders)throws Exception {
		
		TransactionContext transactionContext=responseGenerator.generateTransationContext(httpHeaders);
		try {
			ValidationResult validationResult=validation.validate(RequestType.POST, request);
			brokerCustomerService.saveOrUpdate((BrokerCustomer)validationResult.getObject());
			return responseGenerator.successResponse(transactionContext, messagePropertyService.getMessage("broker.customer.created"), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	@ApiOperation(value = "Allow the login the broker customer",response = Response.class)
	@RequestMapping(value = "/login/brokerCustomer",method = RequestMethod.POST,produces = "applicstion/json",consumes = "application/json")
	ResponseEntity<?> login(@ApiParam(value = "Login a broker customer")@RequestBody LoginRequest request,
			@RequestHeader HttpHeaders httpHeaders) throws Exception{
		
		ErrorDto error=null;
		Map<String, Object> response = new HashMap<>();
		if(null ==request) {
			error = new ErrorDto();
			error.setCode("400");
			error.setMessage("Invalid request payload!");
			response.put("Satuts", 0);
			response.put("error", error);
			return ResponseEntity.badRequest().body(response);
		}
		Optional<BrokerCustomer> optional=brokerCustomerRepository.findByUserName(request.getUserName());
		if(!optional.isPresent()) {
			error=new ErrorDto();
			error.setCode("400");
			error.setMessage("Invalid username1.!");
			response.put("status", 0);
			response.put("error", error);
			return ResponseEntity.badRequest().body(response);
		}
		BrokerCustomer brokerCustomer=optional.get();
		String password =PasswordUtils.getEncryptedPassword(request.getPassword());
		if(!brokerCustomer.getUserName().equals(request.getUserName())) {
			error=new ErrorDto();
			error.setCode("400");
			error.setMessage("Invalid username2.!");
			response.put("status", 0);
			response.put("error", error);
			return ResponseEntity.badRequest().body(response);
					
		}
		if(!brokerCustomer.getPassword().equals(password)) {
			error = new ErrorDto();
			error.setCode("400");
			error.setMessage("Password is wrong.!");
			response.put("Status", 0);
			response.put("error", error);
			return ResponseEntity.badRequest().body(response);
		}
		final String token = jwtTokenUtil.generateToken(brokerCustomer);
		response.put("status", 0);
		response.put("message", "Login successfull.!");
		response.put("jwt", token);
		TransactionContext transactionContext=responseGenerator.generateTransationContext(httpHeaders);
		try {
			return responseGenerator.successResponse(transactionContext, response, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
			
		}
	
	@ApiOperation(value = "Allow the broker customer login otp verify")
	@RequestMapping(value = "/validate/otp",method = RequestMethod.POST,produces = "applicstion/json",consumes = "application/json")
	ResponseEntity<?> validateOtp(@ApiParam(value = "Allow the Otp validate")@RequestBody ValidatorOtpDTO request,
			@RequestHeader HttpHeaders httpHeaders) throws Exception{
		
		Optional<BrokerCustomer> otp=brokerCustomerRepository.findByOtp(request.getOtp());
		System.out.println(otp);
		BrokerCustomer user=otp.get();
		ValidationResult validationResult=validation.validateOtp(RequestType.POST, request, user);
		BrokerCustomer bcOtp=(BrokerCustomer) validationResult.getObject();
		brokerCustomerService.validateBrokerCustomer(user, bcOtp);
		
		
		Map<String , Object> response=new HashMap<String , Object>();
		response.put("status",1         );
		response.put("message", "validation.otp.verified" );
		
		TransactionContext transactionContext=responseGenerator.generateTransationContext(httpHeaders);
		try {
			return responseGenerator.successResponse(transactionContext, messagePropertyService.getMessage("validator.otp"), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return responseGenerator.errorResponse(transactionContext, e.getMessage(),HttpStatus.BAD_REQUEST);
					
		}
				
		
		
	}

}
