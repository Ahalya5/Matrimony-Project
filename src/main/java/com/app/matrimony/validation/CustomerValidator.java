package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.CustomerDto;
import com.app.matrimony.entity.Customer;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.CustomerRepository;
import com.app.matrimony.service.CustomerService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CustomerValidator {

	@Autowired
	private MessagePropertyService messageSource;
	
	private @NonNull CustomerRepository customerRepository;
	
	private @NonNull CustomerService customerService;
	
	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;
	
	public ValidationResult validate(RequestType requestType,CustomerDto request) {
		
		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Customer customerDetails = null;
		

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
			
			Optional<Customer> emailDuplicateObj = customerService.findByEmail(request.getEmail());
			if(emailDuplicateObj.isPresent()) {
				String[] params = new String[] { request.getEmail() };
				errors.add(messageSource.getMessage("email.exist", params));
			}
			
		} else {
			if (ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<Customer> eOptional = customerService.findById(request.getId());
			if (!eOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("city.not.found"));
			}

			customerDetails = eOptional.get();
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getName())) {
			throw new ObjectInvalidException(messageSource.getMessage("full.name.required"));
		}
		
			if (ValidationUtil.isNullOrEmpty(request.getPhoneNo())) {
				errors.add(messageSource.getMessage("user.phone.no.required"));
			}
		
		
		if (ValidationUtil.isNullOrEmpty(request.getEmail())) {
			errors.add(messageSource.getMessage("user.email.required"));
		} else {
			request.setEmail(ValidationUtil.getFormattedString(request.getEmail()));
			if (!ValidationUtil.isValidEmailId(request.getEmail())) {
				errors.add(messageSource.getMessage("user.email.invalid"));
			}
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getAnnualIncome())) {
			errors.add(messageSource.getMessage("anualIncome.required"));
		}
		
		if(ValidationUtil.isNull(request.getCaste())) {
			errors.add(messageSource.getMessage("cate.required"));
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getDosham())) {
			errors.add(messageSource.getMessage("dosham.required"));
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getHeight())) {
			errors.add(messageSource.getMessage("height.required"));
		}
		
		if(ValidationUtil.isNull(request.getDateOfBirth())) {
			errors.add(messageSource.getMessage("dateOfBirth_required"));
		}
		
		if(ValidationUtil.isNull(request.getHeigherEducation())) {
			errors.add(messageSource.getMessage("heigher_required"));
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getPassword())){
			errors.add(messageSource.getMessage("password_required"));
		}
		
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}
		
		if(null==customerDetails) {
			customerDetails = customerDetails.builder().id(request.getId()).name(request.getName()).phoneNo(request.getPhoneNo()).
					          email(request.getEmail()).annualIncome(request.getAnnualIncome()).caste(request.getCaste())
					          .dosham(request.getDosham()).height(request.getHeight()).dateOfBirth(request.getDateOfBirth())
					          .heigherEducation(request.getHeigherEducation()).password(request.getPassword())
					          .build();
		}else {
			customerDetails.setName(request.getName());
			customerDetails.setAnnualIncome(request.getAnnualIncome());
			customerDetails.setCaste(request.getCaste());
			customerDetails.setDateOfBirth(request.getDateOfBirth());
			customerDetails.setPhoneNo(request.getPhoneNo());
			customerDetails.setEmail(request.getEmail());
			customerDetails.setDosham(request.getDosham());
			customerDetails.setHeight(request.getHeight());
			customerDetails.setHeigherEducation(request.getHeigherEducation());
		}
		result.setObject(customerDetails);
		
		return result;
}

}
