package com.todo.app.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class Account {
	private long userId;
	private long teamId;
	private String userName;
	private String teamName;
}
