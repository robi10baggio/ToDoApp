package com.todo.app.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;

@Entity
@Table(name="todo_items")
@Data
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(name="done_flag") 
	private Boolean doneFlag;
	
	@Column(name="time_limit", nullable = false)
	private Date timeLimit;
	
	@Column(name="user_id") 
	private long userId;
	
	@Column(name="team_id") 
	private long teamId;

	@Transient
	private String userName;
	
	@Transient
	private String teamName;
}
