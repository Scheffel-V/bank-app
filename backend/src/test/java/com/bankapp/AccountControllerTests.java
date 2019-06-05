package com.bankapp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import com.bankapp.model.Account;
import com.bankapp.model.Transaction;
import com.bankapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

	@Autowired
    private MockMvc mvc;
	
	private String apiUrl = "/api/v1";
	private ArrayList<Account> accounts = new ArrayList<Account>();
	private User user = new User(1, "testUsername", "testPassword");
	private Account account = new Account(1, 8550, new ArrayList<Transaction>());
	
	
	@Test
	public void contextLoads() {
		this.accounts.add(new Account(-1, 1000, new ArrayList<Transaction>()));
	}
	
	@Test
	public void unauthorizedPostAccountTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(apiUrl + "/users/1/accounts"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedGetAccountTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedGetAllAccountsTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/accounts"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedPutAccountTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put(apiUrl + "/users/1/accounts/1"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedDeleteAccountTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(apiUrl + "/users/1/accounts/1"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void postAccountTest() throws Exception {
		this.contextLoads();
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(this.user);
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);
		String accountJson = mapper.writeValueAsString(this.accounts.get(0));

		result = mvc.perform(
				MockMvcRequestBuilders.post(apiUrl + "/users/1/accounts")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(accountJson)
				)
		.andExpect(status().isCreated())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = JsonPath.parse(stringResponse).read("transactions", transactions.getClass());
		
		Assert.isTrue(id == this.accounts.get(0).getId(), "Account id is wrong.");
		Assert.isTrue(amount == this.accounts.get(0).getAmount(), "Account amount is wrong.");
		Assert.isTrue(transactions.equals(this.accounts.get(0).getTransactions()), "Account transactions are wrong.");
	}
	
	@Test
	public void getAccountTest() throws Exception {
		this.contextLoads();
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(this.user);
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);
		String accountJson = mapper.writeValueAsString(this.accounts.get(0));

		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts/1")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isOk())
		.andReturn();
		
		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = JsonPath.parse(stringResponse).read("transactions", transactions.getClass());
		
		Assert.isTrue(id == this.account.getId(), "Account id is wrong.");
		Assert.isTrue(amount == this.account.getAmount(), "Account amount is wrong.");
		Assert.isTrue(!transactions.isEmpty(), "Account transactions are wrong.");
	}
	
	@Test
	public void putAccountTest() throws Exception {
		this.contextLoads();
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(this.user);
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);
	    Account updatedAccount = this.accounts.get(0);
	    updatedAccount.setAmount(12345);
	    
		String accountJson = mapper.writeValueAsString(updatedAccount);

		result = mvc.perform(
				MockMvcRequestBuilders.put(apiUrl + "/users/1/accounts/2")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(accountJson)
				)
		.andExpect(status().isOk())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = JsonPath.parse(stringResponse).read("transactions", transactions.getClass());
		
		Assert.isTrue(amount == updatedAccount.getAmount(), "Account amount is wrong.");
	}
	
	@Test
	public void deleteAccountTest() throws Exception {
		this.contextLoads();
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(this.user);
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);
	    
	    result = mvc.perform(
				MockMvcRequestBuilders.delete(apiUrl + "/users/1/accounts/3")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isNoContent())
		.andReturn();
	    
		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts/3")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isNotFound())
		.andReturn();
	}
	
	public String parseToken(String jsonToken) {
		String token = jsonToken.substring(0, jsonToken.indexOf(','));
		token = token.replace("token", "");
		token = token.replace("}", "");
		token = token.replace("{", "");
		token = token.replace("\"", "");
		token = token.replace("[", "");
		token = token.replace("]", "");
		token = token.replace(":", "");
	    return token;	
	}
}
