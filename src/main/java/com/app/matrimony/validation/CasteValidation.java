package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.entity.Caste;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.service.CasteService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })

public class CasteValidation {
	private @NonNull MessagePropertyService messageSource;
	private @NonNull CasteService casteService;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, Caste request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Caste casteInfo = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Caste> eOptional = casteService.findById(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("caste.not.found"));
			}

			casteInfo = eOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getCasteName())) {
			throw new ObjectInvalidException(messageSource.getMessage("caste.name.required"));
		}
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == casteInfo) {
			casteInfo = Caste.builder().casteName(request.getCasteName()).description(request.getDescription()).build();
		} else {
			casteInfo.setCasteName(request.getCasteName());
			casteInfo.setDescription(request.getDescription());
		}
		result.setObject(casteInfo);

		return result;
	}

}
