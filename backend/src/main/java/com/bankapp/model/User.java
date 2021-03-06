package com.bankapp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String username;
	
	@NotNull
	@Size(max = 100)
	private String password;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Account> accounts;

	public User() {
		
	}
	
	public User(long id, String username, String password) {
		super();
		this.id = id;
		this.setUsername(username);
		this.setPassword(password);
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Account> getAccounts() {
		return this.accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public String toString() {
		return "ID:" + this.getId().toString() + "USERNAME:" + this.getUsername().toString() + "PASSWORD" + this.getPassword().toString();
	}
}
