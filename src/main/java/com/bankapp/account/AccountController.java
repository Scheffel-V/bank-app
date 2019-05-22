package com.bankapp.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.user.User;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/users/{userId}/accounts")
	public List<Account> getAllAccounts(@PathVariable String userId) {
		return accountService.getAllAccounts(userId);
	}
	
	@RequestMapping("/users/{userId}/accounts/{accountId}")
	public Account getAccount(@PathVariable String accountId) {
		return accountService.getAccount(accountId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/users/{userId}/accounts")
	public void addAccount(@RequestBody Account account, @PathVariable String userId) {
		account.setUser(new User(userId, "", ""));
		accountService.addAccount(account);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/users/{userId}/accounts/{accountId}")
	public void updateAccount(@RequestBody Account account, @PathVariable String userId) {
		account.setUser(new User(userId, "", ""));
		accountService.updateAccount(account);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/users/{userId}/accounts/{accountId}")
	public void deleteAccount(@PathVariable String accountId) {
		accountService.deleteAccount(accountId);
	}
}
