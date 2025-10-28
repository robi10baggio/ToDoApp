package com.todo.app.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	@Query("SELECT id,title,done_flag,time_limit,user_id FROM todo_items WHERE done_flag = False")
	public List<Todo> findByDoneFlagFalse();
	
	@Query("SELECT id,title,done_flag,time_limit,user_id FROM todo_items WHERE done_flag = False and team_id = :team_id")
	public List<Todo> findByDoneFlagFalseAndTeamId(long team_id);
	
	@Query("SELECT id,title,done_flag,time_limit,user_id FROM todo_items WHERE done_flag = True")
	public List<Todo> findByDoneFlagTrue();
	
	@Query("SELECT id,title,done_flag,time_limit,user_id FROM todo_items WHERE done_flag = False and team_id = :team_id")
	public List<Todo> findByDoneFlagTrueAndTeamId(long team_id);
	
	@Query("delete from todo_items where done_flag = True")
	public void deleteByDoneFlagTrue();
}
