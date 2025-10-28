package com.todo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	public List<Todo> findByStatusEquals(Integer status);
	
	public List<Todo> findByStatusEqualsAndTeamId(Integer status, long team_id);
	
	public List<Todo> findByStatusLessThanAndTeamId(Integer status, long team_id);

	public void deleteByStatusEquals(Integer status);
}
