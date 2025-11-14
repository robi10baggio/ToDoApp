package com.todo.app.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.Task;
import com.todo.app.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> selectAll(){
		return taskRepository.findAll();
	}

	public List<Task> selectIncomplete(long team_id) {
		return taskRepository.findByStatusLessThanAndUserTeamIdOrderByDueDate(2, team_id);
	}

	public List<Task> selectComplete(long team_id) {
		return taskRepository.findByStatusEqualsAndUserTeamIdOrderByDueDate(2, team_id);
	}

	public void add(Task task) {
		taskRepository.save(task);
	}

	@Transactional
	public void update(Task task) {
		taskRepository.save(task);
	}

	@Transactional
	public void delete(long id) {
		taskRepository.deleteById(id);
	}
}
