package com.todo.app.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.Todo;
import com.todo.app.repository.TodoRepository;

@Service
public class TodoService {
	
	@Autowired
	TodoRepository todoRepository;

	public List<Todo> selectAll(){
		return todoRepository.findAll();
	}

	public List<Todo> selectIncomplete(long team_id) {
		return todoRepository.findByStatusLessThanAndTeamId(2, team_id);
	}

	public List<Todo> selectComplete(long team_id) {
		return todoRepository.findByStatusEqualsAndTeamId(2, team_id);
	}

	public void add(Todo todo) {
		todoRepository.save(todo);
	}

	@Transactional
	public void update(Todo todo) {
		todoRepository.save(todo);
	}

	@Transactional
	public void delete(long id) {
		todoRepository.deleteById(id);
	}
}
