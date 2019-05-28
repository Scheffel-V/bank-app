package com.bankapp.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bankapp.model.Account;
import com.bankapp.model.User;
import com.bankapp.service.AccountService;
import com.bankapp.service.UserService;

@CrossOrigin
@RestController
public class BasicAuthenticationController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/basicauth")
	public ResponseEntity<User> authenticate() {
		return ResponseEntity.ok(new User());
	}
}
