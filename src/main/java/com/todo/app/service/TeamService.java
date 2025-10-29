package com.todo.app.service;

import java.util.List;
import java.util.Optional;

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
		Optional<Team> team = teamRepository.findById(id);
		return team.get();
	}
}
