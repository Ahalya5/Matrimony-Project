package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.AddBrokerDTO;
import com.app.matrimony.entity.AddBroker;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.AddBrokerRepository;
import com.app.matrimony.service.AddBrokerService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AddBrokerValidatior {
	@Autowired
	private MessagePropertyService messageSource;
	
	private @NonNull AddBrokerRepository AddBrokerRepository;
	
	private @NonNull AddBrokerService addBrokerService;
	
	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;
	
	
public ValidationResult validate(RequestType requestType,AddBrokerDTO request) {
		
		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		AddBroker addBrokerDetails = null;
		

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
		
		
		Optional<AddBroker> emailDuplicateObj = addBrokerService.findByEmail(request.getEmail());
		if(emailDuplicateObj.isPresent()) {
			String[] params = new String[] { request.getEmail() };
			errors.add(messageSource.getMessage("email.exist", params));
	    }
		
		} else {
	   if (ValidationUtil.isNull1(request.getId())) {
		throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
	    }

	   Optional<AddBroker> eOptional = addBrokerService.findById(request.getId());
	  if (!eOptional.isPresent()) {
		throw new ObjectInvalidException(messageSource.getMessage("city.not.found"));
	   }
 
	    addBrokerDetails = eOptional.get();
      }
		
  
		
		
		if(ValidationUtil.isNullOrEmpty(request.getFirstName())) {
			throw new ObjectInvalidException(messageSource.getMessage("first.name.required"));
		}
		
			if (ValidationUtil.isNullOrEmpty(request.getLastName())) {
				errors.add(messageSource.getMessage("lastt.name.]required"));
			}
		
		
		if (ValidationUtil.isNullOrEmpty(request.getEmail())) {
			errors.add(messageSource.getMessage("user.email.required"));
		} else {
			request.setEmail(ValidationUtil.getFormattedString(request.getEmail()));
			if (!ValidationUtil.isValidEmailId(request.getEmail())) {
				errors.add(messageSource.getMessage("email.invalid"));
			}
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getAddressLine1())) {
			errors.add(messageSource.getMessage("address.line1.required"));
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getAddressLine2())) {
			errors.add(messageSource.getMessage("address.line2.required"));
		}
		
		if(ValidationUtil.isNullOrEmpty(request.getMobileNumber())) {
			errors.add(messageSource.getMessage("mobile.number.required"));
		}
		
		
		
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}
		
		
		if(null==addBrokerDetails) {
			addBrokerDetails = addBrokerDetails.builder().id(request.getId()).firstName(request.getFirstName())
					           .lastName(request.getLastName()).mobileNumber(request.getMobileNumber()).
					           email(request.getEmail()).addressLine1(request.getAddressLine1())
					           .addressLine2(request.getAddressLine2()).city(request.getCity())
					           .state(request.getState())
					           .country(request.getCountry())
					           
					           
					           .build();
		}else {
		addBrokerDetails.setFirstName(request.getFirstName());
		addBrokerDetails.setLastName(request.getLastName());
		addBrokerDetails.setMobileNumber(request.getMobileNumber());
		addBrokerDetails.setAddressLine1(request.getAddressLine1());
		addBrokerDetails.setAddressLine2(request.getAddressLine2());
		addBrokerDetails.setEmail(request.getEmail());
		addBrokerDetails.setCity(request.getCity());
		addBrokerDetails.setState(request.getState());
		addBrokerDetails.setCountry(request.getCountry());
		
		
		}
		result.setObject(addBrokerDetails);
		
		return result;
}
}


	
	

