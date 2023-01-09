package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.CurrencyDTO;
import com.app.matrimony.entity.Currency;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.CurrencyRepository;
import com.app.matrimony.service.CurrencyService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CurrencyValidator {

	@Autowired
	private MessagePropertyService messageSource;
	
	private @NonNull CurrencyRepository currencyRepository;
	
	private @NonNull CurrencyService currencyService;
	
	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;
	
public ValidationResult validate(RequestType requestType,CurrencyDTO request) {
		
		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Currency currencyDetails = null;
		

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
			
		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Currency> eOptional = currencyService.getByIds(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("currency.not.found"));
			}

			currencyDetails = eOptional.get();
		}

	
	if(ValidationUtil.isNullOrEmpty(request.getName())) {
		throw new ObjectInvalidException(messageSource.getMessage("full.name.required"));
	}

	if (errors.size() > 0) {
		String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
		throw new ObjectInvalidException(errorMessage);
	}
	
	if(null==currencyDetails) {
		currencyDetails = Currency.builder().id(request.getId()).name(request.getName()).description(request.getDescription())
				          .build();
	}else {
		currencyDetails.setName(request.getName());
		currencyDetails.setDescription(request.getDescription());
	}
	result.setObject(currencyDetails);
	
	return result;
	
}
}

