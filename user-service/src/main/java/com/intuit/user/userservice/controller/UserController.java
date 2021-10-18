/**
 * 
 */
package com.intuit.user.userservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.user.userservice.model.UserInfo;
import com.intuit.user.userservice.request.User;
import com.intuit.user.userservice.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * @author rajmanip
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private ModelMapper modelMapper = new ModelMapper();

	private static final Logger LOGGER = LogManager.getLogger(UserController.class.getName());

	@ApiOperation(value = "Retrieve user details", response = User.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		UserInfo user = userService.getUserDetails(id);
		User response = convertToUser(user);
		LOGGER.info("User with id: {} is searched.", response.getId());
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "create User", response = User.class)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		UserInfo userInfo = userService.createUser(convertToUserInfo(user));
		User response = convertToUser(userInfo);
		return response;
	}

	@ApiOperation(value = "delete User", response = User.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		UserInfo userInfo = userService.deleteUser(id);
		User response = convertToUser(userInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update User", response = User.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<User> deleteUser(@RequestBody User user) {
		UserInfo userInfo = userService.updateUserDetails(convertToUserInfo(user));
		User response = convertToUser(userInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update User", response = User.class)
	@RequestMapping(value = "/{id}/update-balance", method = RequestMethod.PATCH)
	public ResponseEntity<User> updateBalance(@PathVariable Long id, Long amount) {
		UserInfo userInfo = userService.updateUserBalance(id, amount);
		User response = convertToUser(userInfo);
		return ResponseEntity.ok(response);
	}

	private User convertToUser(UserInfo info) {
		User user = modelMapper.map(info, User.class);
		return user;
	}

	private UserInfo convertToUserInfo(User info) {
		UserInfo user = modelMapper.map(info, UserInfo.class);
		return user;
	}
}
