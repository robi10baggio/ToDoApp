package com.todo.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginRepository;
	
	public User loginByAccount(String userName, String password) {
		return loginRepository.findByUserIdAndPassword(userName, password);
	}
	
	public User findById(Long id) {
		Optional<User> user =  loginRepository.findById(id);
		return user.get();
	}

}
