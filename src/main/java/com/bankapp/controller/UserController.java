package com.bankapp.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<User> getUser(@PathVariable long id) {
		try {
			User user = userService.getUser(id);
			return ResponseEntity.ok(user);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		userService.addUser(user);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
		return ResponseEntity.created(location).body(user);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User userUpdated, @PathVariable long id) {
		try {
			User user =  userService.getUser(id);
			user.setUsername(userUpdated.getUsername());
	        user.setPassword(userUpdated.getPassword());
	        userService.updateUser(user);
	        return ResponseEntity.ok(user);
		} catch(NoSuchElementException expcetion) {
			return ResponseEntity.notFound().build();
		} 
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		try {
			User user = userService.getUser(id);
			userService.deleteUser(user);
			return ResponseEntity.noContent().build();
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
