package com.bankapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.model.User;
import com.bankapp.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping("/users/{id}")
	public User getAccount(@PathVariable long id) {
		return userService.getUser(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public void addUser(@Valid @RequestBody User user) {
		userService.addUser(user);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
	public void updateUser(@Valid @RequestBody User userUpdated, @PathVariable long id) {
		User user =  userService.getUser(id);
        user.setUsername(userUpdated.getUsername());
        user.setPassword(userUpdated.getPassword());
        userService.updateUser(user);    
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}
}
