package com.app.matrimony.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.matrimony.dto.RoleDTO;
import com.app.matrimony.entity.Role;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.response.Response;
import com.app.matrimony.response.ResponseGenerator;
import com.app.matrimony.response.TransactionContext;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.RoleService;
import com.app.matrimony.validation.RoleValidation;
import com.app.matrimony.validation.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/role")
@Api(value = "Role Rest API", produces = "application/json", consumes = "application/json")
@RestController
public class RoleController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	private @NonNull ResponseGenerator responseGenerator;
	private @NonNull MessagePropertyService messageSource;
	private @NonNull RoleService roleService;
	private @NonNull RoleValidation validatorService;

	@ApiOperation(value = "Allows to create Role.", response = Response.class)
	@PostMapping(value = "/roleCreate", produces = "application/json")
	public ResponseEntity<?> createRole(@ApiParam(value = "The Role request payload") @RequestBody RoleDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);

		try {
			roleService.saveorUpdate(request);
			return responseGenerator.successResponse(context, messageSource.getMessage("role.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch Role list.", response = Response.class)
	@GetMapping(value = "/getRolelist", produces = "application/json")
	public ResponseEntity<?> getRolelist(@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			List<Role> role = roleService.findAll();

			return responseGenerator.successGetResponse(context, messageSource.getMessage("role.get"), role,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch particular Role.", response = Response.class)
	@GetMapping(value = "/getRole/{id}", produces = "application/json")
	public ResponseEntity<?> getInvoicebyid(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<Role> role = roleService.findById(id);

			return responseGenerator.successGetResponse(context, messageSource.getMessage("role.get"), role,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to update existing customer.", response = Response.class)
	@PutMapping(value = "/update/role", produces = "application/json")
	public ResponseEntity<?> updateRole(@ApiParam(value = "The Update request payload") @RequestBody RoleDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		// ValidationResult validationResult =
		// validatorService.validate(RequestType.PUT, request);

		try {
			roleService.saveorUpdate(request);
			return responseGenerator.successResponse(context, messageSource.getMessage("role.update"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to delete role by id.", response = Response.class)
	@DeleteMapping(value = "/delete/{roleId}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("roleId") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			Optional<Role> role = roleService.findById(id);
			Role obj = role.get();
			obj.setStatus(Status.DELETED);
			obj.setCreatedBy("EBRAIN");
			obj.setCreatedOn(new Date());
			roleService.saveorUpdate(obj);
			return responseGenerator.successResponse(context, messageSource.getMessage("role.delete"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, messageSource.getMessage("role.invalid.delete"),
					HttpStatus.BAD_REQUEST);
		}
	}
}