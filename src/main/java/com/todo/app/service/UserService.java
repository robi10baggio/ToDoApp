package com.todo.app.service;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository loginRepository;
	
	public User loginAuth(String userName, String password) {
		return loginRepository.findByUserIdAndPassword(userName, password);
	}
	
	public User findById(Long id) {
		Optional<User> user =  loginRepository.findById(id);
		return user.get();
	}
	
	@Transactional
	public void regist(User user) {
		loginRepository.save(user);
	}
}
