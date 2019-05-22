package com.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public List<Account> findByUserId(long userId);
}
