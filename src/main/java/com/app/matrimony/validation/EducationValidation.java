package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.entity.Education;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.service.EducationService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })

public class EducationValidation {
	private @NonNull MessagePropertyService messageSource;
	private @NonNull EducationService educationService;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, Education request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Education educationInfo = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Education> eOptional = educationService.findById(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("education.not.found"));
			}

			educationInfo = eOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getName())) {
			throw new ObjectInvalidException(messageSource.getMessage("education.name.required"));
		}
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == educationInfo) {
			educationInfo = Education.builder().name(request.getName()).description(request.getDescription())
					.build();
		} else {
			educationInfo.setName(request.getName());
			educationInfo.setDescription(request.getDescription());
		}
		result.setObject(educationInfo);

		return result;
	}

}
