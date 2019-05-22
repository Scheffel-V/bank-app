package com.bankapp.account;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {

	public List<Account> findByUserId(String userId);
}
