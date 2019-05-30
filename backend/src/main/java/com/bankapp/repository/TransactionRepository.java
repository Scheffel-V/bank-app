package com.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.model.Account;
import com.bankapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	public List<Transaction> findByOriginAccountId(long accountId);
}
