package com.bankapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping("/users/{userId}/accounts")
	public List<Account> getAllAccountsByUserId(@PathVariable long userId) {
		return accountService.getAllAccountsByUserId(userId);
	}
	
	@RequestMapping("/users/{userId}/accounts/{accountId}")
	public Account getAccount(@PathVariable long accountId) {
		return accountService.getAccountById(accountId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/users/{userId}/accounts")
	public void addAccount(@Valid @RequestBody Account account, @PathVariable long userId) {
		User user = userService.getUser(userId);
		account.setUser(user);
		accountService.addAccount(account);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/users/{userId}/accounts/{accountId}")
	public void updateAccount(@Valid @RequestBody Account accountUpdated, @PathVariable long accountId) {
		Account account = accountService.getAccountById(accountId);
		account.setAmount(accountUpdated.getAmount());
		accountService.updateAccount(account);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/users/{userId}/accounts/{accountId}")
	public void deleteAccount(@PathVariable long accountId) {
		accountService.deleteAccount(accountId);
	}
}
