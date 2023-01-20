package com.app.matrimony.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.matrimony.dto.UserResponseDTO;
import com.app.matrimony.entity.User;
import com.app.matrimony.enumaration.Status;
import com.app.matrimony.repository.RoleRepository;
import com.app.matrimony.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class UserService implements UserDetailsService {

	private @NonNull UserRepository userRepository;

	private @NonNull RoleRepository roleRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		User user = userOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
//
//	public Optional<User> changePassword(String email) {
//		return userRepository.findByEmail(email);
//	}

	public void saveOrUpdate(User obj) {
		if (obj.getUserId() == null) {
			String password = generateRandomPassword(6);
			System.out.println("Password: " + password);
			String encrypPAW = obj.setAndEncryptPassword(password);
			obj.setUserName(obj.getEmail());
			obj.setPassword(encrypPAW);

//			obj.setAndEncryptPassword(obj.getPassword());

		}
		userRepository.saveAndFlush(obj);
	}

	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	public Optional<User> getById(UUID id) {
		return userRepository.findById(id);
	}

	public List<UserResponseDTO> findByUserRoleType(String status) {
		return userRepository.findByUserRoleType(status);
	}

	public void deleteUser(UUID id) {
		if (id != null) {
			Optional<User> UOMObj = userRepository.findById(id);
			User obj = UOMObj.get();
			obj.setStatus(Status.INACTIVE);
			obj.setDeletedBy("ebrain");
			obj.setDeletedOn(new Date());
			userRepository.saveAndFlush(obj);
		}
	}

	public static String generateRandomPassword(int len) {

		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	public Optional<User> findByMobileNo(String phoneNo) {
		return userRepository.findByMobileNo(phoneNo);
	}

	public Optional<User> findByUserEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	

}