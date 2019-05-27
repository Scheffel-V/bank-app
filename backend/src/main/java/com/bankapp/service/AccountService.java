package com.bankapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.model.Account;
import com.bankapp.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		accountRepository.findAll().forEach(accounts::add);
		return accounts;
	}
	
	public List<Account> getAllAccountsByUserId(long userId) {
		List<Account> accounts = new ArrayList<Account>();
		accountRepository.findByUserId(userId).forEach(accounts::add);
		return accounts;
	}
	
	public Account getAccount(long id) throws NoSuchElementException{
		return accountRepository.findById(id).get();
	}
	
	public void addAccount(Account account) {
		accountRepository.save(account);
	}
	
	public void updateAccount(Account account) {
		accountRepository.save(account);
	}
	
	public void deleteAccount(Account account) {
		accountRepository.delete(account);;
	}
}
