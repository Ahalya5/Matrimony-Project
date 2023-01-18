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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.MotherTongueDTO;

import com.app.matrimony.entity.MotherTongue;
import com.app.matrimony.entity.Religion;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.MotherTongueService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/motherTongue")
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Api(value = "Allow the MotherTongue Api", produces = "application/json", consumes = "application/json")
public class MotherTongueController {

	private static final Logger logger = Logger.getLogger(MotherTongueDTO.class);

	@Autowired
	private MotherTongueService motherTongueService;

	@Autowired
	private MessagePropertyService messagePropertyService;

	@Autowired
	private ResponseGenerator responseGenerator;

	@ApiOperation(value = "Allow the create request", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "Allow the create payload") @RequestBody MotherTongue request,
			@RequestHeader HttpHeaders httpHeaders) throws Exception {
		TransactionContext transactionContext = responseGenerator.generateTransationContext(httpHeaders);
		try {
			motherTongueService.saveOrUpdate(request);
			return responseGenerator.successResponse(transactionContext, "create.motherTongue", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	@ApiOperation(value = "Allow the Religion Api", response = Response.class)
	@GetMapping(value = "/get/religion", produces = "application/json")
	public ResponseEntity<?> findAll(@RequestHeader HttpHeaders httpHeaders) throws Exception {
		TransactionContext transactionContext = responseGenerator.generateTransationContext(httpHeaders);
		try {
			List<MotherTongue> list = motherTongueService.getAll();
			return responseGenerator.successGetResponse(transactionContext, "getAll.mother.tongue", list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow the Religion Api", response = Response.class)
	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ResponseEntity<?> findbyId(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeaders)
			throws Exception {
		TransactionContext transactionContext = responseGenerator.generateTransationContext(httpHeaders);
		try {
			Optional<MotherTongue> optional = motherTongueService.getById(id);
			return responseGenerator.successGetResponse(transactionContext, "getAll.mother.tongue", optional, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(transactionContext, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to religion update.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(
			@ApiParam(value = "The management update request payload") @RequestBody MotherTongue request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			motherTongueService.saveOrUpdate(request);
			return responseGenerator.successResponse(context, "update.mother.tongue", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allows to religion deleted.", response = Response.class)
	@PutMapping(value = "/deleted/{id}", produces = "application/json")
	public ResponseEntity<?> update(@RequestParam("id")UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			motherTongueService.deletedById(id);
			return responseGenerator.successResponse(context, "deleted.mother.tongue", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
