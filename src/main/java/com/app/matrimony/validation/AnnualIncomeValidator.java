package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.AnnualIncomeDTO;
import com.app.matrimony.entity.AnnualIncome;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.AnnualIncomeRepository;
import com.app.matrimony.service.AnnualIncomeService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AnnualIncomeValidator {
	
	@Autowired
	private MessagePropertyService messageSource;
	
	private @NonNull AnnualIncomeRepository annualIncomeRepository;
	
	private @NonNull AnnualIncomeService annualIncomeService;
		
		List<String> errors = null;
		List<String> errorsObj = null;
		Optional<Subject> subject = null;

		public ValidationResult validate(RequestType requestType,AnnualIncomeDTO request) {
			
			errors = new ArrayList<>();
			ValidationResult result = new ValidationResult();
			AnnualIncome annualIncomeDetails = null;
			

			if (requestType.equals(RequestType.POST)) {
				if (!ValidationUtil.isNull1(request.getId())) {
					throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
				}
				
			} else {
				if (ValidationUtil.isNull1(request.getId())) {
					throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
				}

				Optional<AnnualIncome> Optional = annualIncomeService.getByIds(request.getId());
				if (!Optional.isPresent()) {
					throw new ObjectInvalidException(messageSource.getMessage("currency.not.found"));
				}

				annualIncomeDetails = Optional.get();
			}

		
		if(ValidationUtil.isNullOrEmpty(request.getName())) {
			throw new ObjectInvalidException(messageSource.getMessage("full.name.required"));
		}

		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}
		
		if(null==annualIncomeDetails) {
			annualIncomeDetails = AnnualIncome.builder().id(request.getId()).name(request.getName()).description(request.getDescription())
					          .build();
		}else {
			annualIncomeDetails.setName(request.getName());
			annualIncomeDetails.setDescription(request.getDescription());
		}
		result.setObject(annualIncomeDetails);
		
		return result;
		
	}
	
}
