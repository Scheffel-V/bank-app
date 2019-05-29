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
import com.bankapp.model.User;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
	
	private ArrayList<User> userArray = new ArrayList<User>();
	
	private String postUserid = "4";
	private String postUserUsername = "existentuser";
	private String postUserPassword = "password";
	private String postUserJson = "{\"username\":\"" + postUserUsername + "\", \"password\":\""
	    	 + postUserPassword + "\"}";
	
	
	private String firstUserJson;
	private String secondUserJson;
	private String thirdUserJson;
	
	private String apiUrl = "/api/v1";
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void contextLoads() {
		userArray.add(new User(1, "testUsername", "testPassword"));
		userArray.add(new User(2, "testUsername2", "testPassword2"));
		userArray.add(new User(3, "testUsername3", "testPassword3"));
		
		firstUserJson = "{\"username\":\"" + userArray.get(0).getUsername() + "\", \"password\":\""
		    	 + userArray.get(0).getPassword() + "\"}";
		secondUserJson = "{\"username\":\"" + userArray.get(1).getUsername() + "\", \"password\":\""
		    	 + userArray.get(1).getPassword() + "\"}";
		thirdUserJson = "{\"username\":\"" + userArray.get(2).getUsername() + "\", \"password\":\""
		    	 + userArray.get(2).getPassword() + "\"}";
	}
	
	@Test
	public void unauthorizedGetUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/users"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedPutUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put(apiUrl + "/users"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void unauthorizedDeleteUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(apiUrl + "/users"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void postUserTest() throws Exception {
		this.contextLoads();
		String body = postUserJson;
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post(apiUrl + "/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(body)
				)
		.andExpect(status().isCreated())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		String username = JsonPath.parse(stringResponse).read("username", String.class);
		String password = JsonPath.parse(stringResponse).read("password", String.class);
		List<Account> accounts = new ArrayList<Account>();
		accounts = JsonPath.parse(stringResponse).read("accounts", accounts.getClass());
		
		Assert.isTrue(id == Long.parseLong(this.postUserid), "User id is wrong.");
		Assert.isTrue(username.equals(this.postUserUsername), "User username is wrong.");
		Assert.isTrue(password.equals(this.postUserPassword), "User password is wrong.");
		Assert.isTrue(accounts.isEmpty(), "User accounts are wrong.");
	}
	
	@Test
	public void getTokenTest() throws Exception {
		this.contextLoads();
		String body = firstUserJson;
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);

	    mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/users")
	        .header("Authorization", "Bearer " + token))
	        .andExpect(status().isOk());
	}
	
	@Test
	public void getUserTest() throws Exception {
		this.contextLoads();
		String body = firstUserJson;
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);

		System.out.println(token);
		
		body = postUserJson;
		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users")
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isOk())
		.andReturn();
		
		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("$[0].id", Long.class);
		String username = JsonPath.parse(stringResponse).read("$[0].username", String.class);
		String password = JsonPath.parse(stringResponse).read("$[0].password", String.class);
		List<Account> accounts = new ArrayList<Account>();
		accounts = JsonPath.parse(stringResponse).read("$[0].accounts", accounts.getClass());
		
		Assert.isTrue(id == userArray.get(0).getId(), "User id is wrong.");
		Assert.isTrue(username.equals(userArray.get(0).getUsername()), "User username is wrong.");
		Assert.isTrue(password.equals(userArray.get(0).getPassword()), "User password is wrong.");
		Assert.isTrue(accounts.isEmpty(), "User accounts are wrong.");
	} 
		
	@Test
	public void putUserTest() throws Exception {
		this.contextLoads();
		String body = secondUserJson;
		
	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    String token = this.parseToken(response);
	    
		body = secondUserJson;
		body.replace(userArray.get(1).getUsername(), "testUsernameUpdated");
		body.replace(userArray.get(1).getPassword(), "testPasswordUpdated");
		
		System.out.println(token);
		result = mvc.perform(
				MockMvcRequestBuilders.put(apiUrl + "/users/" + userArray.get(1).getId())
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(body)
				)
		.andExpect(status().isOk())
		.andReturn();

		String stringResponse = result.getResponse().getContentAsString();
		Long id = JsonPath.parse(stringResponse).read("id", Long.class);
		String username = JsonPath.parse(stringResponse).read("username", String.class);
		String password = JsonPath.parse(stringResponse).read("password", String.class);
		List<Account> accounts = new ArrayList<Account>();
		accounts = JsonPath.parse(stringResponse).read("accounts", accounts.getClass());
		
		Assert.isTrue(id == userArray.get(1).getId(), "User id is wrong.");
		Assert.isTrue(username.equals(userArray.get(1).getUsername()), "User updated username is wrong.");
		Assert.isTrue(password.equals(userArray.get(1).getPassword()), "User updated password is wrong.");
		Assert.isTrue(accounts.isEmpty(), "User accounts are wrong.");
	}	
	
	@Test
	public void deleteUserTest() throws Exception {
		this.contextLoads();
		String body = secondUserJson;
		
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
				MockMvcRequestBuilders.delete(apiUrl + "/users/" + userArray.get(2).getId())
				.header("Authorization", "Bearer " + token)
				)
		.andExpect(status().isNoContent())
		.andReturn();
	    
		result = mvc.perform(
				MockMvcRequestBuilders.get(apiUrl + "/users/" + userArray.get(2).getId())
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(body)
				)
		.andExpect(status().isNotFound())
		.andReturn();
	}
	
	
	public String parseToken(String jsonToken) {
	    jsonToken = jsonToken.replace("token", "");
	    jsonToken = jsonToken.replace("}", "");
	    jsonToken = jsonToken.replace("{", "");
	    jsonToken = jsonToken.replace("\"", "");
	    jsonToken = jsonToken.replace(":", "");
	    return jsonToken;	
	}
}
