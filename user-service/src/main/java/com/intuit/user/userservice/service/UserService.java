/**
 * 
 */
package com.intuit.user.userservice.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.user.userservice.model.UserInfo;
import com.intuit.user.userservice.repository.UserRepository;

/**
 * @author rajmanip
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserInfo createUser(UserInfo user) {
		user = userRepository.save(user);
		return user;
	}

	public UserInfo getUserDetails(Long userId) {
		Optional<UserInfo> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}else {
			throw new NoSuchElementException("Data not found for the requested user");
		}
	}

	public UserInfo updateUserDetails(UserInfo user) {
		user = userRepository.save(user);
		return user;
	}

	public UserInfo updateUserBalance(Long userId, Long amount) {
		UserInfo user = getUserDetails(userId);
		user.setBalance(user.getBalance() + amount);
		user = userRepository.save(user);
		return user;
	}

	public UserInfo deleteUser(Long userId) {
		UserInfo user = getUserDetails(userId);
		userRepository.deleteById(userId);
		return user;
	}

}
