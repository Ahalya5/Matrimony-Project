package com.app.matrimony.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.AnnualIncomeDTO;
import com.app.matrimony.entity.AnnualIncome;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.AnnualIncomeService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.validation.AnnualIncomeValidator;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/annualIncome")
@Api(value = "AnnualIncome Rest API", produces = "application/json", consumes = "application/json")
public class AnnualIncomeController {
	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull MessagePropertyService messageSource;
	
	private @NonNull AnnualIncomeService annualIncomeService;
	
	private @NonNull AnnualIncomeValidator annualIncomeValidator; 
	private static final Logger logger = Logger.getLogger(AnnualIncomeController.class);
	
	@ApiOperation(value = "Allows to create annualIncome.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The create request payload") @RequestBody AnnualIncomeDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = annualIncomeValidator.validate(RequestType.POST, request);
		annualIncomeService.saveOrUpdate((AnnualIncome)(validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, "annualIncome.create", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to update annualIncome.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "The update request payload") @RequestBody AnnualIncomeDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ValidationResult validationResult = annualIncomeValidator.validate(RequestType.PUT, request);
		
		try {
			annualIncomeService.saveOrUpdate((AnnualIncome)(validationResult.getObject()));
			return responseGenerator.successResponse(context,"annualIncome.details.updated", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	@ApiOperation(value = "Allows to get all annualIncome", response = Response.class)
	@GetMapping(value = "/getAnnualIncomeList", produces = "application/json")
	public ResponseEntity<?> getAllCurrencies(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			List<AnnualIncome> annualIncomeList = annualIncomeService.getAll();
			return responseGenerator.successGetResponse(context, "annualIncome.list.getAll",
					annualIncomeList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to get particular annualIncome.", response = Response.class)
	@GetMapping(value = "/getAnnualIncome/{id}", produces = "application/json")
	public ResponseEntity<?> getAnnualIncomeInfoById(@PathVariable("id") UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<AnnualIncome> annualIncome  = annualIncomeService.getByIds(id);

			return responseGenerator.successGetResponse(context, "annualIncome.get.sucessfully",
					annualIncome, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to delete particular annualIncome", response = Response.class)
	@PutMapping(value = "/delete/{annualIncome_id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("annualIncome_id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {

			annualIncomeService.deleteById(id);
			return responseGenerator.successResponse(context, "annualIncome.data.deleted",
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}







