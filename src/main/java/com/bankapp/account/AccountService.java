package com.bankapp.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> getAllAccounts(String userId) {
		List<Account> accounts = new ArrayList<Account>();
		accountRepository.findByUserId(userId).forEach(accounts::add);
		
		return accounts;
	}
	
	public Account getAccount(String id) {
		return accountRepository.findById(id).get();
	}
	
	public void addAccount(Account account) {
		accountRepository.save(account);
	}
	
	public void updateAccount(Account account) {
		accountRepository.save(account);
	}
	
	public void deleteAccount(String id) {
		accountRepository.deleteById(id);;
	}
}
