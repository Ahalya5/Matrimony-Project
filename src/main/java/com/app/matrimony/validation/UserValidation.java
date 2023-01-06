package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.UserDTO;
import com.app.matrimony.entity.Role;
import com.app.matrimony.entity.User;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.RoleRepository;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.service.UserService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class UserValidation {

	@Autowired
	private MessagePropertyService messageSource;

	@Autowired
	private UserService userService;

	private @NonNull RoleRepository roleRepository;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, UserDTO request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		User user = null;

		if (requestType.equals(RequestType.POST)) {
			if (ValidationUtil.isNull(request.getUserId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<User> mobileDuplicateObj = userService.findByMobileNo(request.getMobileNo());
			if (mobileDuplicateObj.isPresent()) {
				String[] params = new String[] { request.getMobileNo() };
				errors.add(messageSource.getMessage("phone.no.duplicate", params));
			}

			Optional<User> userDuplicateMailObj = userService.findByUserEmail(request.getEmail());
			if (userDuplicateMailObj.isPresent()) {
				errors.add(messageSource.getMessage("user.email.duplicate"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getFirstName())) {
				throw new ObjectInvalidException(messageSource.getMessage("first.name.required"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getLastName())) {
				throw new ObjectInvalidException(messageSource.getMessage("last.name.required"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getEmail())) {
				errors.add(messageSource.getMessage("user.email.required"));
			} else {
				request.setEmail(ValidationUtil.getFormattedString(request.getEmail()));
				if (!ValidationUtil.isValidEmailId(request.getEmail())) {
					errors.add(messageSource.getMessage("user.email.invalid"));
				}
			}

			if (ValidationUtil.isNullOrEmpty(request.getMobileNo())) {
				errors.add(messageSource.getMessage("phone.no.required"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getAlternatePhoneNo())) {
				throw new ObjectInvalidException(messageSource.getMessage("alternate.phone.no.required"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getAddressLine1())) {
				throw new ObjectInvalidException(messageSource.getMessage("address.line1.required"));
			}

			if (ValidationUtil.isNullOrEmpty(request.getNote())) {
				throw new ObjectInvalidException(messageSource.getMessage("notes.required"));
			}

		} else {
			if (ValidationUtil.isNull(request.getUserId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<User> userOptional = userService.findById(request.getUserId());
			if (!userOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("user.not.found"));
			}

			user = userOptional.get();
		}

		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == user) {

			user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
					.mobileNo(request.getMobileNo()).email(request.getEmail()).userName(request.getUserName())
					.password(request.getPassword()).alternatePhoneNo(request.getAlternatePhoneNo())
					.gender(request.getGender()).addressLine1(request.getAddressLine1())
					.addressLine2(request.getAddressLine2()).cityId(request.getCityId()).stateId(request.getStateId())
					.countryId(request.getCountryId()).roleId(request.getRoleId()).note(request.getNote()).build();
		} else {
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setUserName(request.getUserName());
			user.setMobileNo(request.getMobileNo());
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());
			user.setAlternatePhoneNo(request.getAlternatePhoneNo());
			user.setGender(request.getGender());
			user.setAddressLine1(request.getAddressLine1());
			user.setAddressLine2(request.getAddressLine2());
			user.setCityId(request.getCityId());
			user.setStateId(request.getStateId());
			user.setCountryId(request.getCountryId());
			user.setRoleId(request.getRoleId());
			user.setStatus(request.getStatus());
			user.setNote(request.getNote());

		}
		result.setObject(user);

		return result;

	}

}