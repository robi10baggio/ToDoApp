package com.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.User;

public interface LoginRepository extends JpaRepository<User, Long> {
	public User findByUserIdAndPassword(String userId, String password);
}
