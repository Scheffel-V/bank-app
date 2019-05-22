package com.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
