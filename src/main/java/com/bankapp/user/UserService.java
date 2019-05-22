package com.bankapp.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		
		return users;
	}
	
	public User getUser(String id) {
		return userRepository.findById(id).get();
	}
	
	public void addUser(User user) {
		userRepository.save(user);
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);;
	}
}
