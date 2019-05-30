package com.bankapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.model.Account;
import com.bankapp.model.Transaction;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Transaction> getAllTransactions() {
		List<Account> accounts = new ArrayList<Account>();
		List<Transaction> transactions = new ArrayList<Transaction>();
		accountRepository.findAll().forEach(accounts::add);
		for(Account account : accounts) {
			for(Transaction transaction : account.getTransactions()) {
				transactions.add(transaction);
			}
		}
		return transactions;
	}
	
	public List<Transaction> getAllTransactionsByAccountId(long accountId) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findByOriginAccountId(accountId).forEach(transactions::add);
		
		return transactions;
	}
	
	public Transaction getTransaction(long id) throws NoSuchElementException {
		return transactionRepository.findById(id).get();
	}
	
	public void addTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	public void updateTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	public void deleteTransaction(Transaction transaction) {
		transactionRepository.delete(transaction);
	}
}
