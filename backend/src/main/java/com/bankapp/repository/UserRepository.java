package com.bankapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankapp.model.Account;
import com.bankapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
}
