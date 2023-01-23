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

	public void saveOrUpdate(BrokerCustomer brokerCustomer) {
		final long expirInterval = 5L * 60 * 1000;

		String otps = new DecimalFormat("000000").format(new Random().nextInt(999999));
		System.out.println(otps);

		Date expireDate = new Date(System.currentTimeMillis() + expirInterval);
		System.out.println(expireDate);
		brokerCustomer.setExpiryDate(expireDate);
		BrokerCustomer brokerCustomerObj = new BrokerCustomer();

		brokerCustomerObj.setAddBrokerId(brokerCustomer.getAddBrokerId());
		brokerCustomerObj.setAnnualIncome(brokerCustomer.getAnnualIncome());
		brokerCustomerObj.setCasteId(brokerCustomer.getCasteId());
		brokerCustomerObj.setCityId(brokerCustomer.getCityId());
		brokerCustomerObj.setCountryId(brokerCustomer.getCountryId());
		brokerCustomerObj.setDisability(brokerCustomer.getDisability());
		brokerCustomerObj.setPassword(brokerCustomer.getPassword());
		brokerCustomerObj.setDosham(brokerCustomer.getDosham());
		brokerCustomerObj.setId(brokerCustomer.getId());
		brokerCustomerObj.setEmail(brokerCustomer.getEmail());
		brokerCustomerObj.setExpiryDate(expireDate);
		brokerCustomerObj.setOtp(otps);
		brokerCustomerObj.setGender(brokerCustomer.getGender());
		brokerCustomerObj.setIsOtpVerified(brokerCustomer.getIsOtpVerified());
		brokerCustomerObj.setGothram(brokerCustomer.getGothram());
		brokerCustomerObj.setName(brokerCustomer.getName());
		brokerCustomerObj.setMaritalStatus(brokerCustomer.getMaritalStatus());
		brokerCustomerObj.setOccupation(brokerCustomer.getOccupation());
		brokerCustomerObj.setProfileFor(brokerCustomer.getProfileFor());
		brokerCustomerObj.setPhoneNo(brokerCustomer.getPhoneNo());
		brokerCustomerObj.setHeight(brokerCustomer.getHeight());
		brokerCustomerObj.setDateOfBirth(brokerCustomer.getDateOfBirth());
		brokerCustomerObj.setReligion(brokerCustomer.getReligion());
		brokerCustomerObj.setMotherTounge(brokerCustomer.getMotherTounge());
		brokerCustomerObj.setSubCaste(brokerCustomer.getSubCaste());
		brokerCustomerObj.setFamilyStatus(brokerCustomer.getFamilyStatus());
		brokerCustomerObj.setFamilyType(brokerCustomer.getFamilyType());
		brokerCustomerObj.setFamilyValue(brokerCustomer.getFamilyValue());
		brokerCustomerObj.setHighestEducation(brokerCustomer.getHighestEducation());
		brokerCustomerObj.setJobType(brokerCustomer.getJobType());
		brokerCustomerObj.setAbout(brokerCustomer.getAbout());
		brokerCustomerObj.setUserName(brokerCustomer.getUserName());
		brokerCustomerObj.setRoleType(brokerCustomer.getRoleType());
		brokerCustomerObj.setBasicInfoObj(brokerCustomer.getBasicInfoObj());
		brokerCustomerObj.setFamilyInfoObj(brokerCustomer.getFamilyInfoObj());
		brokerCustomerObj.setLifeStyleInfoObj(brokerCustomer.getLifeStyleInfoObj());
		brokerCustomerObj.setReligionInfoObj(brokerCustomer.getReligionInfoObj());
		brokerCustomerObj.setHoroscopeDetailsObj(brokerCustomer.getHoroscopeDetailsObj());
		brokerCustomerRepository.saveAndFlush(brokerCustomerObj);

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
