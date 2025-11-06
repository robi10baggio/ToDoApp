package com.todo.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.Team;
import com.todo.app.repository.TeamRepository;

@Service
public class TeamService {
	@Autowired 
	TeamRepository teamRepository;
	
	public List<Team> findAll() {
		return   teamRepository.findAll();
	}
	
	public Team findById(Long id) {
		return teamRepository.findById(id).orElse(null);
	}
}
