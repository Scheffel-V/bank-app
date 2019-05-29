package com.bankapp.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bankapp.model.User;
import com.bankapp.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable long id) {
		try {
			User user = userService.getUser(id);
			return ResponseEntity.ok(user);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		userService.addUser(user);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
		return ResponseEntity.created(location).body(user);
	}

	@PutMapping("/users/{id}")
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

	@DeleteMapping("/users/{id}")
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
