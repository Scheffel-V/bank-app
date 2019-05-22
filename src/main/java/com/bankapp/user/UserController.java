package com.bankapp.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping("/users/{id}")
	public User getAccount(@PathVariable String id) {
		return userService.getUser(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/users")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/users/{id}")
	public void updateUser(@RequestBody User user, @PathVariable String id) {
		userService.updateUser(user);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/users/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
}
