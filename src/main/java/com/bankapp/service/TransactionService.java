package com.bankapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.model.Transaction;
import com.bankapp.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findAll().forEach(transactions::add);
		return transactions;
	}
	
	public Transaction getTransactionById(long id) {
		return transactionRepository.findById(id).get();
	}
	
	public void addTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	public void updateTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	public void deleteTransaction(long id) {
		transactionRepository.deleteById(id);
	}
}
