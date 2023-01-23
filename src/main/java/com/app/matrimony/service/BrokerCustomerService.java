package com.app.matrimony.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.entity.BasicInfo;
import com.app.matrimony.entity.BasicInfo.BasicInfoBuilder;
import com.app.matrimony.entity.BrokerCustomer;
import com.app.matrimony.entity.LifeStyleInfo;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.BrokerCustomerRepository;
import com.app.matrimony.repository.RoleRepository;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;

@Service
public class BrokerCustomerService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BrokerCustomerRepository brokerCustomerRepository;



	public void saveOrUpdate(BrokerCustomer userObj) {
		final long expirInterval = 5L * 60 * 1000;

		String otps = new DecimalFormat("000000").format(new Random().nextInt(999999));
		System.out.println(otps);

		Date expireDate = new Date(System.currentTimeMillis() + expirInterval);
		System.out.println(expireDate);
		userObj.setExpiryDate(expireDate);

		
		BrokerCustomer brokerContomer = BrokerCustomer.builder().userName(userObj.getUserName())
				.profileFor(userObj.getProfileFor()).phoneNo(userObj.getPhoneNo()).gender(userObj.getGender())
				.dateOfBirth(userObj.getDateOfBirth()).religion(userObj.getReligion())
				.motherTounge(userObj.getMotherTounge()).email(userObj.getEmail()).password(userObj.getPassword())
				.casteId(userObj.getCasteId()).stateId(userObj.getStateId()).cityId(userObj.getCityId())
				.countryId(userObj.getCountryId()).subCaste(userObj.getSubCaste()).gothram(userObj.getGothram())
				.dosham(userObj.getDosham()).maritalStatus(userObj.getMaritalStatus()).height(userObj.getHeight())
				.familyStatus(userObj.getFamilyStatus()).familyType(userObj.getFamilyType())
				.familyValue(userObj.getFamilyValue()).disability(userObj.getDisability())
				.highestEducation(userObj.getHighestEducation()).annualIncome(userObj.getAnnualIncome())
				.occupation(userObj.getOccupation()).about(userObj.getAbout()).jobType(userObj.getJobType())
				.name(userObj.getName()).otp(otps).isOtpVerified(userObj.getIsOtpVerified()).expiryDate(expireDate)
				.addBrokerId(userObj.getAddBrokerId())
				.lifeStyleInfoObj(userObj.getLifeStyleInfoObj())
				.basicInfoObj(userObj.getBasicInfoObj()).familyInfoObj(userObj.getFamilyInfoObj())
				.religionInfoObj(userObj.getReligionInfoObj()).hoioscopeDetailsObj(userObj.getHoioscopeDetailsObj())
				.build();
		brokerCustomerRepository.saveAndFlush(brokerContomer);
		


	}

	public Optional<BrokerCustomer> getByPhoneNo(String phoneNo) {
		return brokerCustomerRepository.findByPhoneNo(phoneNo);
	}

	public Optional<BrokerCustomer> getByUsername(String username) {
		return brokerCustomerRepository.findByUserName(username);
	}

	public Optional<BrokerCustomer> getByEmail(String email) {
		return brokerCustomerRepository.findByEmail(email);
	}

	public Optional<BrokerCustomer> getById(UUID id) {
		return brokerCustomerRepository.findById(id);
	}

	public Optional<BrokerCustomer> findByRoleType(String admin) {
		return brokerCustomerRepository.findByRoleType(admin);
	}

	@Transactional
	public void validateBrokerCustomer(BrokerCustomer user, BrokerCustomer otp) {

		otp.setStatus(Status.INACTIVE);
		brokerCustomerRepository.saveAndFlush(otp);

		user.setIsOtpVerified(true);
		brokerCustomerRepository.saveAndFlush(user);
		return;
	}
}
