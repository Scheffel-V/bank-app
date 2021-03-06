package com.bankapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.bankapp.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "transactions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
public class Transaction {

	@JsonView(View.Summary.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonView(View.Summary.class)
	@NotNull
	private double amount;
	
	@JsonView(View.Summary.class)
	@ManyToOne
	@JoinColumn(name = "origin_account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Account originAccount;
	
	@JsonView(View.Summary.class)
	@ManyToOne
	@JoinColumn(name = "destiny_account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Account destinyAccount;

	@JsonView(View.Summary.class)
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private TransactionState state = TransactionState.STARTED;
	
	public Transaction() {
		
	}
	
	public Transaction(long id, double amount, Account originAccount, Account destinyAccount, TransactionState state) {
		super();
		this.id = id;
		this.amount = amount;
		this.originAccount = originAccount;
		this.destinyAccount = destinyAccount;
		this.state = state;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getOriginAccount() {
		return originAccount;
	}

	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}

	public Account getDestinyAccount() {
		return destinyAccount;
	}

	public void setDestinyAccount(Account destinyAccount) {
		this.destinyAccount = destinyAccount;
	}	
	
	public TransactionState getState() {
		return this.state;
	}
	
	public void setState(TransactionState state) {
		this.state = state;
	}
	
	public String toString() {
		return "ID:" + this.id + " AMOUNT:" + this.amount + " ORIGIN ACCOUNT ID:" + this.getOriginAccount().getId() + "DESTINY ACCOUNT ID:" + this.getDestinyAccount().getId();
	}
}
