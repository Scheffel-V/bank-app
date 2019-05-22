package com.bankapp.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.bankapp.user.User;

@Entity
public class Account {

	@Id
	private String id;
	private double amount;
	@ManyToOne
	private User user;	

	public Account() {
		
	}
	
	public Account(String id, double amount, String userId) {
		super();
		this.id = id;
		this.amount = amount;
		this.user = new User(userId, "", "");
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
