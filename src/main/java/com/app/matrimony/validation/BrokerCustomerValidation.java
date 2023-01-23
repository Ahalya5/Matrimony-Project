package com.app.matrimony.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.matrimony.controllerAdvice.ObjectInvalidException;
import com.app.matrimony.dto.BrokerCustomerDTO;
import com.app.matrimony.dto.ValidatorOtpDTO;
import com.app.matrimony.entity.BrokerCustomer;
import com.app.matrimony.entity.PasswordUtils;
import com.app.matrimony.entity.Role;
import com.app.matrimony.enumaration.RequestType;
import com.app.matrimony.repository.BrokerCustomerRepository;
import com.app.matrimony.repository.RoleRepository;
import com.app.matrimony.service.BrokerCustomerService;
import com.app.matrimony.service.MessagePropertyService;
import com.app.matrimony.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class BrokerCustomerValidation {
	@Autowired
	MessagePropertyService messageSource;

	private @NonNull BrokerCustomerRepository brokerCustomerRepository;

	private @NonNull BrokerCustomerService brokerCustomerService;

	private @NonNull RoleRepository roleRepository;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	/**
	 * method for product type validation Added by Ulaganathan
	 *
	 * @param httpHeader
	 * @return ValidationResult
	 * @throws Exception
	 */

	public ValidationResult validate(RequestType requestType, BrokerCustomerDTO request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		BrokerCustomer user = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull1(request.getId())) {
				throw new ObjectInvalidException("Invalid.request.payload");
			}
			if (ValidationUtil.isNullOrEmpty(request.getUserName())) {
				errors.add("user.name.required");
			} else {
				request.setUserName(ValidationUtil.getFormattedString(request.getUserName()));
				if (!ValidationUtil.isValidName(request.getUserName())) {
					errors.add("user.name.invalid");
				}
			}
//			if (ValidationUtil.isNullOrEmpty(request.getEmail())) {
//				errors.add("email.required");
//			} else {
//				request.setEmail(ValidationUtil.getFormattedString(request.getEmail()));
//				if (!ValidationUtil.isValidName(request.getEmail())) {
//					errors.add("email.invalid");
//				}
//			}
			Optional<BrokerCustomer> userOptional = brokerCustomerService.getByPhoneNo(request.getPhoneNo());
			if (userOptional.isPresent()) {
				String[] params = new String[] { request.getPhoneNo() };
				errors.add("user.phone.duplicate");
			}

		} else {
			if (ValidationUtil.isNull1(request.getId()))
				throw new ObjectInvalidException("invalid.request.payload");

			Optional<BrokerCustomer> userOptional = brokerCustomerService.getById(request.getId());
			if (!userOptional.isPresent()) {
				throw new ObjectInvalidException("id.not.found");
			}
			user = userOptional.get();
		}
		if (ValidationUtil.isNullOrEmpty(request.getName())) {
			errors.add("name.required");
		} else {
			request.setName(ValidationUtil.getFormattedString(request.getName()));
			if (!ValidationUtil.isValidName(request.getName())) {
				errors.add("name.Invalid");
			}
		}

		if (ValidationUtil.isNullOrEmpty(request.getPassword())) {
			errors.add("password.required");
		}
		if (ValidationUtil.isNull(request.getDateOfBirth())) {
			errors.add("bateOfBirth.required");
		}

		if (ValidationUtil.isNull1(request.getReligion())) {
			errors.add("religion.required");
		}
		if (ValidationUtil.isNull1(request.getMotherTounge())) {
			errors.add("motherTounge.required");
		}
		if (ValidationUtil.isNullOrEmpty(request.getSubCaste())) {
			errors.add("subCaste.required");
		}
		if (ValidationUtil.isNull1(request.getCasteId())) {
			errors.add("caste.required");
		}
		if (ValidationUtil.isNullOrEmpty(request.getDosham())) {
			errors.add("dosam.required");

		}
		if (ValidationUtil.isNullOrEmpty(request.getGothram())) {
			errors.add("gothram.required");

		}
		if (ValidationUtil.isNullOrEmpty(request.getHeight())) {
			errors.add("height.required");
		}

		if (ValidationUtil.isNull1(request.getHighestEducation())) {
			errors.add("highestEducation.required");
		}
		if (ValidationUtil.isNullOrEmpty(request.getAnnualIncome())) {
			errors.add("annualIncome.required");
		}
		if (ValidationUtil.isNullOrEmpty(request.getOccupation())) {
			errors.add("occupation.required");
		}
		if (ValidationUtil.isNull1(request.getCityId())) {
			errors.add("city.id.required");

		}
		if (ValidationUtil.isNull1(request.getCountryId())) {
			errors.add("country.id.required");

		}
		if (ValidationUtil.isNull1(request.getStateId())) {
			errors.add("state.id.required");

		}
		if(ValidationUtil.isNullOrEmpty(request.getAbout())) {
			errors.add("about.requried");
		}
		
		if(ValidationUtil.isNull1(request.getAddBrokerId())) {
			errors.add("add.broker.id.requried");
		}
		String encrptPassword = PasswordUtils.getEncryptedPassword(request.getPassword());
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}
		if (null == user) {
			String admin = "ADMIN";
			Optional<BrokerCustomer> userOption = brokerCustomerService.findByRoleType(admin);

			Optional<Role> role = null;

			role = roleRepository.findByRoleName(request.getRoleType().toString());
			user = BrokerCustomer.builder().userName(request.getUserName()).password(encrptPassword).id(request.getId())
					.profileFor(request.getProfileFor()).name(request.getName()).dateOfBirth(request.getDateOfBirth())
					.gender(request.getGender()).motherTounge(request.getMotherTounge()).phoneNo(request.getPhoneNo())
					.religion(request.getReligion()).maritalStatus(request.getMaritalStatus()).casteId(request.getCasteId())
					.subCaste(request.getSubCaste()).gothram(request.getGothram()).dosham(request.getDosham())
					.email(request.getEmail()).height(request.getHeight()).familyStatus(request.getFamilyStatus())
					.familyType(request.getFamilyType()).familyValue(request.getFamilyValue())
					.roleType(request.getRoleType())
					.disability(request.getDisability()).highestEducation(request.getHighestEducation())
					.occupation(request.getOccupation()).jobType(request.getJobType()).roleType(request.getRoleType())
					.annualIncome(request.getAnnualIncome()).cityId(request.getCityId()).stateId(request.getStateId())
					.otp(request.getOtp()).isOtpVerified(request.getIsOtpVerified()).countryId(request.getCountryId())
					.about(request.getAbout()).expiryDate(request.getExpiryDate())
					.addBrokerId(request.getAddBrokerId())
					.basicInfoObj(request.getBasicInfoObj())
					.lifeStyleInfoObj(request.getLifeStyleInfoObj())
					.familyInfoObj(request.getFamilyInfoObj()).religionInfoObj(request.getReligionInfoObj())
					.horoscopeDetailsObj(request.getHoroscopeDetailsObj()).build();

		} else {
			user.setUserName(request.getUserName());
			user.setAndEncryptPassword(request.getPassword());
			user.setProfileFor(request.getProfileFor());
			user.setName(request.getName());
			user.setEmail(request.getEmail());
			user.setPhoneNo(request.getPhoneNo());
			user.setDisability(request.getDisability());
			user.setReligion(request.getReligion());
			user.setGender(request.getGender());
			user.setMaritalStatus(request.getMaritalStatus());
			user.setCasteId(request.getCasteId());
			user.setSubCaste(request.getSubCaste());
			user.setGothram(request.getGothram());
			user.setDosham(request.getDosham());
			user.setFamilyStatus(request.getFamilyStatus());
			user.setFamilyType(request.getFamilyType());
			user.setFamilyValue(request.getFamilyValue());
			user.setCountryId(request.getCountryId());
			user.setCityId(request.getCityId());
			user.setHeight(request.getHeight());
			user.setHighestEducation(request.getHighestEducation());
			user.setMotherTounge(request.getMotherTounge());
			user.setStateId(request.getStateId());
			user.setId(request.getId());
			user.setRoleType(request.getRoleType());
			user.setOtp(request.getOtp());
			user.setAnnualIncome(request.getAnnualIncome());
			user.setExpiryDate(request.getExpiryDate());
			user.setCountryId(request.getCountryId());
			user.setExpiryDate(request.getExpiryDate());
			user.setIsOtpVerified(request.getIsOtpVerified());
			user.setJobType(request.getJobType());
			user.setAddBrokerId(request.getAddBrokerId());
			user.setAbout(request.getAbout());
			user.setBasicInfoObj(request.getBasicInfoObj());
			user.setLifeStyleInfoObj(request.getLifeStyleInfoObj());
			user.setFamilyInfoObj(request.getFamilyInfoObj());
			user.setReligionInfoObj(request.getReligionInfoObj());
			user.setHoroscopeDetailsObj(request.getHoroscopeDetailsObj());
			
		}
		result.setObject(user);
		return result;
	}
		public ValidationResult validateOtp(RequestType requestType, ValidatorOtpDTO request, BrokerCustomer user) {
			errors = new ArrayList<>();

			if (ValidationUtil.isNullOrEmpty(request.getPhoneNo())) {
				errors.add(messageSource.getMessage("signup.mobile.required"));
			} else {
				request.setPhoneNo(ValidationUtil.getFormattedString(request.getPhoneNo()));
				if (!ValidationUtil.isValidMobileNumber(request.getPhoneNo())) {
					errors.add(messageSource.getMessage("signup.mobile.invalid"));
				}
			}
			if (ValidationUtil.isNullOrEmpty(request.getOtp())) {
				errors.add(messageSource.getMessage("validation.otp.required"));
			} else {
				request.setOtp(ValidationUtil.getFormattedString(request.getOtp()));
			}

			Optional<BrokerCustomer> otpObj = brokerCustomerRepository.findByPhoneNo(request.getPhoneNo());

			if (!otpObj.isPresent()) {
				errors.add(messageSource.getMessage("validation.otp.invalid"));
			}

			if (!otpObj.get().getOtp().equals(request.getOtp())) {
				errors.add(messageSource.getMessage("validation.otp.wrong"));
			}
			ValidationResult result = new ValidationResult();
			if (errors.size() > 0) {
				String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
				throw new ObjectInvalidException(errorMessage);
			}
			result.setObject(otpObj.get());
			return result;
	 }

}

//}
