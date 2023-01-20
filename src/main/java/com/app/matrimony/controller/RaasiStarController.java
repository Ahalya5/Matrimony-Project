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

import com.app.matrimony.dto.RaasiStarDto;
import com.app.matrimony.entity.RaasiStar;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.RaasiStarService;
import com.app.matrimony.validation.RaasiStarValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RestController
@RequestMapping("/api/raasiStar")
@Api(value = "RaasiStar Rest API", produces = "application/json", consumes = "application/json")
public class RaasiStarController {

	private static final Logger logger = Logger.getLogger(RaasiStarController.class);

	private @NonNull ResponseGenerator responseGenerator;
	private @NonNull MessagePropertyService messageSource;
	private @NonNull RaasiStarService raasiStarService;
	private @NonNull RaasiStarValidation raasiStarValidation;

	@ApiOperation(value = "Allows to create RaasiStarDetails.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(
			@ApiParam(value = "The RaasiStar request payload") @RequestBody RaasiStarDto request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = raasiStarValidation.validate(RequestType.POST, request);
		raasiStarService.saveOrUpdate((RaasiStar) (validationResult.getObject()));

		try {
			return responseGenerator.successResponse(context, "Raasi.Star.created", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to update RaasiStar.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateCustomer(
			@ApiParam(value = "The Update request payload") @RequestBody RaasiStarDto request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		ValidationResult validationResult = raasiStarValidation.validate(RequestType.PUT, request);

		try {
			raasiStarService.saveOrUpdate((RaasiStar) validationResult.getObject());
			return responseGenerator.successResponse(context, "Raasi.star.updated", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch active raasi.", response = Response.class)
	@GetMapping(value = "/getActiveRaasiInfo", produces = "application/json")
	public ResponseEntity<?> getActiveraasis(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			List<RaasiStar> raasiStars = raasiStarService.getActives();

			return responseGenerator.successGetResponse(context, "active.raasi.stars.got", raasiStars, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch particular raasi.", response = Response.class)
	@GetMapping(value = "/getRaasiInfo/{raasi_star_id}", produces = "application/json")
	public ResponseEntity<?> getStarInfoById(@PathVariable("raasi_star_id") UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			RaasiStarDto raasiStarDto = raasiStarService.getRStarById(id);

			return responseGenerator.successGetResponse(context, "raasi.star.get", raasiStarDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch Raasilist.", response = Response.class)
	@GetMapping(value = "/getAllRaasi", produces = "application/json")
	public ResponseEntity<?> getStarlists(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			List<RaasiStarDto> raasiStarDtos = raasiStarService.getAllRStar();

			return responseGenerator.successGetResponse(context, "raasi.stars.get", raasiStarDtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to delete existing Raasi.", response = Response.class)
	@PutMapping(value = "/delete/{raasi_star_Id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("raasi_star_Id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {

			raasiStarService.deleteById(id);
			return responseGenerator.successResponse(context, "raasi.star.deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
