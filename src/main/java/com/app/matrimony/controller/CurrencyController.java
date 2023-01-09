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

import com.app.matrimony.dto.CurrencyDTO;
import com.app.matrimony.entity.Currency;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.CurrencyService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.validation.CurrencyValidator;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/currenci")
@Api(value = "Currency Rest API", produces = "application/json", consumes = "application/json")
public class CurrencyController {

	private @NonNull MessagePropertyService messageSource;
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull CurrencyService currencyService;
	
	private @NonNull CurrencyValidator currencyValidator;
private static final Logger logger = Logger.getLogger(CurrencyController.class);
	
	@ApiOperation(value = "Allows to create currencies.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The create request payload") @RequestBody CurrencyDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = currencyValidator.validate(RequestType.POST, request);
		currencyService.saveOrUpdate((Currency)(validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, "currency.create", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to update currencies.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "The update request payload") @RequestBody CurrencyDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ValidationResult validationResult = currencyValidator.validate(RequestType.PUT, request);
		
		try {
			currencyService.saveOrUpdate((Currency)(validationResult.getObject()));
			return responseGenerator.successResponse(context, "currency.details.updated", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	@ApiOperation(value = "Allows to get all currencies", response = Response.class)
	@GetMapping(value = "/getCurrencyList", produces = "application/json")
	public ResponseEntity<?> getAllCurrencies(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			List<Currency> currencyList = currencyService.getAll();
			return responseGenerator.successGetResponse(context,"currency.list.getAll",
					currencyList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to get particular currencies.", response = Response.class)
	@GetMapping(value = "/getCurrency/{id}", produces = "application/json")
	public ResponseEntity<?> getCustomerInfoById(@PathVariable("id") UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<Currency> currencies = currencyService.getByIds(id);

			return responseGenerator.successGetResponse(context,"currency.get.sucessfully",
					currencies, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to delete particular currency", response = Response.class)
	@PutMapping(value = "/delete/{currency_id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("currency_id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {

			currencyService.deleteById(id);
			return responseGenerator.successResponse(context,"currency.data.deleted",
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}




