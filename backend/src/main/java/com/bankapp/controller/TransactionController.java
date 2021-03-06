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

import com.bankapp.View;
import com.bankapp.model.Account;
import com.bankapp.model.Transaction;
import com.bankapp.model.TransactionState;
import com.bankapp.service.AccountService;
import com.bankapp.service.TransactionService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TransactionController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	@JsonView(View.Summary.class)
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return transactionService.getAllTransactions();
	}
		
	@JsonView(View.Summary.class)
	@GetMapping("/users/{userId}/accounts/{accountId}/transactions")
	public List<Transaction> getAllTransactionsByAccountId(@PathVariable long accountId) {
		return transactionService.getAllTransactionsByAccountId(accountId);
	}
	
	@JsonView(View.Summary.class)
	@GetMapping("/users/{userId}/accounts/{accountId}/transactions/{transactionId}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable long transactionId) {
		try {
			Transaction transaction = transactionService.getTransaction(transactionId);
			return ResponseEntity.ok(transaction);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/users/{userId}/accounts/{accountId}/transactions")
	public ResponseEntity<Transaction> addTransaction(@Valid @RequestBody Transaction transaction, @PathVariable long accountId) {
		try {
			Account originAccount = accountService.getAccount(accountId);
			transaction.setOriginAccount(originAccount);
			Account destinyAccount = accountService.getAccount(transaction.getDestinyAccount().getId());
			if(originAccount.getId().equals(destinyAccount.getId())) {
				return ResponseEntity.notFound().build();
			}
			transactionService.addTransaction(transaction);
			URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(transaction.getId())
	                .toUri();
			return ResponseEntity.created(location).body(transaction);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/users/{userId}/accounts/{accountId}/transactions/{transactionId}")
	public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction transactionUpdated, @PathVariable long transactionId, @PathVariable long accountId) {
		try {
			Account newDestinyAccount = accountService.getAccount(transactionUpdated.getDestinyAccount().getId());
			Account originAccount = accountService.getAccount(accountId);
			if(originAccount.getId().equals(newDestinyAccount.getId())) {
				return ResponseEntity.notFound().build();
			}
			Transaction transaction = transactionService.getTransaction(transactionId);
			transaction.setDestinyAccount(newDestinyAccount);
			transaction.setAmount(transactionUpdated.getAmount());
			transaction.setState(transactionUpdated.getState());
			transactionService.updateTransaction(transaction);
			return ResponseEntity.ok(transaction);
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{userId}/accounts/{accountId}/transactions/{transactionId}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable long transactionId) {
		try {
			Transaction transaction = transactionService.getTransaction(transactionId);
			transactionService.deleteTransaction(transaction);
			return ResponseEntity.noContent().build();
		} catch(NoSuchElementException exception) {
			return ResponseEntity.notFound().build();
		}
	}

}
