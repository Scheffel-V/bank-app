package com.bankapp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankAppApplicationTests {


    @Autowired
    private MockMvc mvc;

	@Test
	public void contextLoads() {
	}
	
	
	private String username = "existentuser";
	private String password = "password";
	private String userJson = "{\"username\":\"" + username + "\", \"password\":\""
	    	 + password + "\"}";
	
	private String apiUrl = "/api/v1";
	private String token;

	/*
	@Test
	public void existentUserCanGetTokenAndAuthentication() throws Exception {	    
		String body = userJson;
		mvc.perform(
				MockMvcRequestBuilders.post(apiUrl + "/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(body)
				)
		.andExpect(status().isCreated())
		.andReturn();

	    MvcResult result = mvc.perform(
	    		MockMvcRequestBuilders.post("/authenticate")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(body)
	            )
	            .andExpect(status().isOk())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    response = response.replace("token", "");
	    response = response.replace("}", "");
	    response = response.replace("{", "");
	    response = response.replace("\"", "");
	    response = response.replace(":", "");
	    String token = response;

	    mvc.perform(MockMvcRequestBuilders.get(apiUrl + "/users")
	        .header("Authorization", "Bearer " + token))
	        .andExpect(status().isOk());
	} */

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
