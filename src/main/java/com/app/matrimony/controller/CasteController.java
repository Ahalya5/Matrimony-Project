package com.app.matrimony.controller;

import java.util.List;
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

import com.app.matrimony.dto.CasteDTO;
import com.app.matrimony.entity.Caste;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.CasteService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.validation.CasteValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/caste")
@Api(value = "caste Rest API", produces = "application/json", consumes = "application/json")
public class CasteController {
	private @NonNull ResponseGenerator responseGenerator;
	private @NonNull MessagePropertyService messageSource;
	private @NonNull CasteService casteService;
	private @NonNull CasteValidation validatorService;

	private static final Logger logger = Logger.getLogger(CasteController.class);

	@ApiOperation(value = "Allows to create casteDetails.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createCaste(@ApiParam(value = "The caste request payload") @RequestBody Caste request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);
		casteService.saveOrUpdate((Caste) (validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, "caste.created", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch list .", response = Response.class)
	@GetMapping(value = "/getList", produces = "application/json")
	public ResponseEntity<?> getCasteList(
			@ApiParam(value = "The site list payload") @RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext transactionContext = responseGenerator.generateTransationContext(httpHeader);
		try {

			List<CasteDTO> casteList = casteService.getAll();
			return responseGenerator.successGetResponse(transactionContext, "caste.getList", casteList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch particular caste.", response = Response.class)
	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ResponseEntity<?> getCaste(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext transactionContext = responseGenerator.generateTransationContext(httpHeader);
		try {
			CasteDTO caste = casteService.getById(id);
			return responseGenerator.successGetResponse(transactionContext, "caste.get", caste, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to update caste.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateCaste(@ApiParam(value = "The Update request payload") @RequestBody Caste request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = validatorService.validate(RequestType.PUT, request);

		try {
			casteService.saveOrUpdate((Caste) validationResult.getObject());
			return responseGenerator.successResponse(context, "caste.update", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allows to update existing caste.", response = Response.class)
	@PutMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> deleteCaste(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		casteService.delete(id);
		try {
			return responseGenerator.successResponse(context,"caste.delete", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
