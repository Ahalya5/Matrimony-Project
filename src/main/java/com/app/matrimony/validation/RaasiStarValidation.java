package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.RaasiStarDto;
import com.app.matrimony.entity.RaasiStar;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.RaasiStarService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class RaasiStarValidation {

	private @NonNull MessagePropertyService messageSource;
	private @NonNull RaasiStarService raasiStarService;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, RaasiStarDto request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		RaasiStar raasiStarInfo = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<RaasiStar> eOptional = raasiStarService.getRaasiById(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("raasi.star.not.found"));
			}

			raasiStarInfo = eOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getName())) {
			throw new ObjectInvalidException(messageSource.getMessage("name.required"));
		}
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == raasiStarInfo) {
			raasiStarInfo = RaasiStar.builder().name(request.getName()).description(request.getDescription())
					.build();
		} else {
			raasiStarInfo.setName(request.getName());
			raasiStarInfo.setDescription(request.getDescription());
		}
		result.setObject(raasiStarInfo);

		return result;
	}

}
