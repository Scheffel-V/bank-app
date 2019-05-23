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

import com.bankapp.model.Account;
import com.bankapp.model.User;
import com.bankapp.service.AccountService;
import com.bankapp.service.UserService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}
	
	@RequestMapping("/users/{userId}/accounts")
	public List<Account> getAllAccountsByUserId(@PathVariable long userId) {
		return accountService.getAllAccountsByUserId(userId);
	}
	
	@RequestMapping("/users/{userId}/accounts/{accountId}")
	public ResponseEntity<Account> getAccount(@PathVariable long accountId) {
		try {
			Account account = accountService.getAccount(accountId);
			return ResponseEntity.ok(account);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/users/{userId}/accounts")
	public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account, @PathVariable long userId) {
		try {
			User user = userService.getUser(userId);
			account.setUser(user);
			accountService.addAccount(account);
			URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(account.getId())
	                .toUri();
			return ResponseEntity.created(location).body(account);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/users/{userId}/accounts/{accountId}")
	public ResponseEntity<Account> updateAccount(@Valid @RequestBody Account accountUpdated, @PathVariable long accountId) {
		try {
			Account account = accountService.getAccount(accountId);
			account.setAmount(accountUpdated.getAmount());
			accountService.updateAccount(account);
	        return ResponseEntity.ok(account);
		} catch(NoSuchElementException expcetion) {
			return ResponseEntity.notFound().build();
		} 
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/users/{userId}/accounts/{accountId}")
	public ResponseEntity<Void> deleteAccount(@PathVariable long accountId) {
		try {
			Account account = accountService.getAccount(accountId);
			accountService.deleteAccount(account);
			return ResponseEntity.noContent().build();
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
