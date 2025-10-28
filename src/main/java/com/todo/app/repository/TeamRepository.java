package com.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
