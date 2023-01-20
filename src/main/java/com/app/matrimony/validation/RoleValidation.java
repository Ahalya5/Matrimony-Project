package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.RoleDTO;
import com.app.matrimony.entity.Role;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.RoleService;
import com.app.matrimony.util.ValidationUtil;

//dharsh
@Service
public class RoleValidation {
	@Autowired
	private MessagePropertyService messageSource;

	@Autowired
	private RoleService roleService;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, RoleDTO request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Role role = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
		} else {
			if (ValidationUtil.isNull(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Role> roleOptional = roleService.findById(request.getId());
			if (!roleOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("role.not.found"));
			}

			role = roleOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getDescription())) {
			errors.add(messageSource.getMessage("description.required"));
		}

		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == role) {
			role = Role.builder().roleName(request.getRoleName()).description(request.getDescription())

					.build();
		} else {
			role.setRoleName(request.getRoleName());
			role.setDescription(request.getDescription());
		}

		result.setObject(role);
		return result;

	}
}
