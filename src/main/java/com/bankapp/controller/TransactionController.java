package com.bankapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.model.Transaction;
import com.bankapp.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return transactionService.getAllTransactions();
	}
	
	@RequestMapping("/transactions/{transactionId}")
	public Transaction getTransaction(@PathVariable long transactionId) {
		return transactionService.getTransactionById(transactionId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/transactions")
	public void addTransaction(@Valid @RequestBody Transaction transaction) {
		transactionService.addTransaction(transaction);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/transactions/{transactionId}")
	public void updateAccount(@Valid @RequestBody Transaction transactionUpdated, @PathVariable long transactionId) {
		Transaction transaction = transactionService.getTransactionById(transactionId);
		transaction.setAmount(transactionUpdated.getAmount());
		transaction.setDestinyAccount(transactionUpdated.getDestinyAccount());
		transaction.setOriginAccount(transactionUpdated.getDestinyAccount());
		transaction.setState(transactionUpdated.getState());
		transactionService.updateTransaction(transaction);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/transaction/{transactionId}")
	public void deleteTransaction(@PathVariable long transactionId) {
		transactionService.deleteTransaction(transactionId);
	}

}
