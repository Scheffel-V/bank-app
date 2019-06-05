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
import com.bankapp.model.TransactionState;
import com.bankapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {

	@Autowired
    private MockMvc mvc;
	
	private String apiUrl = "/api/v1";
	private ArrayList<Account> accounts = new ArrayList<Account>();
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private User user = new User(1, "testUsername", "testPassword");
	private Account account = new Account(2, 8550, new ArrayList<Transaction>());
	private Transaction transaction = new Transaction(1, 100, null, this.account, TransactionState.STARTED);
	
	@Test
	public void contextLoads() {
		this.accounts.add(new Account(-1, 1000, new ArrayList<Transaction>()));
		this.accounts.add(new Account(2, 1000, new ArrayList<Transaction>()));
		this.transactions.add(new Transaction(-1, 1000, new Account(1, 1000, new ArrayList<Transaction>()), new Account(2, 1000, new ArrayList<Transaction>()), TransactionState.STARTED));
	}
	
	@Test
	public void unauthorizedPostTransactionTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(apiUrl + "/users/1/accounts/1/transactions"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedGetTransactionTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts/1/transactions/1"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedGetAllTransactionsTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/transactions"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedPutTransactionTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put(apiUrl + "/users/1/accounts/1/transactions/1"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedDeleteTransactionTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(apiUrl + "/users/1/accounts/1/transactions/1"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void postTransactionTest() throws Exception {
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
	    
		String transactionJson = mapper.writeValueAsString(this.transactions.get(0));
		result = mvc.perform(
				MockMvcRequestBuilders.post(apiUrl + "/users/1/accounts/1/transactions")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(transactionJson)
				)
		.andExpect(status().isCreated())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		Account destinyAccount = JsonPath.parse(stringResponse).read("destinyAccount", Account.class);
		
		Assert.isTrue(id == this.transactions.get(0).getId(), "Transaction id is wrong.");
		Assert.isTrue(amount == this.transactions.get(0).getAmount(), "Transaction amount is wrong.");
		Assert.isTrue(destinyAccount.getId().equals(this.transactions.get(0).getDestinyAccount().getId()), "Transaction destiny account is wrong.");
	}
	
	@Test
	public void getTransactionTest() throws Exception {
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
		String accountJson = mapper.writeValueAsString(this.transactions.get(0));

		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts/1/transactions/1")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isOk())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		Account destinyAccount = JsonPath.parse(stringResponse).read("destinyAccount", Account.class);
		
		Assert.isTrue(id == this.transaction.getId(), "Transaction id is wrong.");
		Assert.isTrue(amount == this.transaction.getAmount(), "Transaction amount is wrong.");
		Assert.isTrue(this.transaction.getDestinyAccount().getId().equals(destinyAccount.getId()), "Transaction destiny account is wrong.");
	}
	
	@Test
	public void putTransactionTest() throws Exception {
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
	    
	    Transaction updatedTransaction = this.transactions.get(0);
	    updatedTransaction.setAmount(12345);
	    updatedTransaction.setState(TransactionState.FINISHED);
	    updatedTransaction.setDestinyAccount(this.accounts.get(1));
	    
		String transactionJson = mapper.writeValueAsString(updatedTransaction);
		
		result = mvc.perform(
				MockMvcRequestBuilders.put(apiUrl + "/users/1/accounts/1/transactions/2")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(transactionJson)
				)
		.andExpect(status().isOk())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long amount = JsonPath.parse(stringResponse).read("amount", Long.class);
		Account destinyAccount = JsonPath.parse(stringResponse).read("destinyAccount", Account.class);
		
		Assert.isTrue(amount == updatedTransaction.getAmount(), "Transaction amount is wrong.");
		//Assert.isTrue(destinyAccount.getId().equals(updatedTransaction.getDestinyAccount().getId()), "Transaction destiny account is wrong.");
	}
	
	@Test
	public void deleteTransactionTest() throws Exception {
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
				MockMvcRequestBuilders.delete(apiUrl + "/users/1/accounts/1/transactions/2")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isNoContent())
		.andReturn();
	    
		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users/1/accounts/1/transactions/2")
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
