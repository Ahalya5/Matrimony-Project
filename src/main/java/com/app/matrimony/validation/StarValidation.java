package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.StarDto;
import com.app.matrimony.entity.Star;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.StarService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class StarValidation {

	private @NonNull MessagePropertyService messageSource;
	private @NonNull StarService service;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, StarDto request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Star starInfo = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Star> eOptional = service.getById(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("star.not.found")); 
			}

			starInfo = eOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getName())) {
			throw new ObjectInvalidException(messageSource.getMessage("name.required"));
		}
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == starInfo) {
			starInfo = Star.builder().name(request.getName()).description(request.getDescription()).build();
		} else {
			starInfo.setName(request.getName());
			starInfo.setDescription(request.getDescription());
		}
		result.setObject(starInfo);

		return result;
	}

}
