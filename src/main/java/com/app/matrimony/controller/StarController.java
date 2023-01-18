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

import com.app.matrimony.dto.StarDto;
import com.app.matrimony.entity.Star;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.StarService;
import com.app.matrimony.validation.StarValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RestController
@RequestMapping("/api/star")
@Api(value = "Star Rest API", produces = "application/json", consumes = "application/json")
public class StarController {

	private static final Logger logger = Logger.getLogger(StarController.class);

	private @NonNull ResponseGenerator responseGenerator;
	private @NonNull MessagePropertyService messageSource;
	private @NonNull StarService starService;
	private @NonNull StarValidation starValidation;

	@ApiOperation(value = "Allows to create starDetails.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The star request payload") @RequestBody StarDto request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = starValidation.validates(RequestType.POST, request);
		starService.saveOrUpdate((Star) (validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, "Star.created", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to update Star.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateCustomer(
			@ApiParam(value = "The Update request payload") @RequestBody StarDto request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = starValidation.validates(RequestType.PUT, request);

		try {
			starService.saveOrUpdate((Star) validationResult.getObject());
			return responseGenerator.successResponse(context, "customer.updated", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch active stars.", response = Response.class)
	@GetMapping(value = "/getActiveStarInfo", produces = "application/json")
	public ResponseEntity<?> getActiveraasis(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			List<Star> Stars = starService.getActives();

			return responseGenerator.successGetResponse(context, "active.stars.got", Stars, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch particular star.", response = Response.class)
	@GetMapping(value = "/getStarInfo/{star_id}", produces = "application/json")
	public ResponseEntity<?> getStarInfoById(@PathVariable("star_id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			StarDto starDto = starService.getStarById(id);

			return responseGenerator.successGetResponse(context, "star.get", starDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch Starlist.", response = Response.class)
	@GetMapping(value = "/getAllStars", produces = "application/json")
	public ResponseEntity<?> getStarlists(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			List<StarDto> starss = starService.getAllStar();

			return responseGenerator.successGetResponse(context, "stars.get", starss, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to delete existing Customer.", response = Response.class)
	@PutMapping(value = "/delete/{starId}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("starId") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {

			starService.deleteById(id);
			return responseGenerator.successResponse(context, "star.deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
