package com.todo.app.controller;

import java.sql.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Team;
import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.form.TodoForm;
import com.todo.app.model.Account;
import com.todo.app.service.LoginService;
import com.todo.app.service.TeamService;
import com.todo.app.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	TodoService todoService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	TeamService teamService;


	@GetMapping("/")
	public String index(TodoForm todoForm, Model model) {
		
		List<Todo> list = todoService.selectIncomplete(account.getTeamId());
		for (Todo todo:list) {
			long userId = todo.getUserId();
			User user = loginService.findById(userId);
			todo.setUserName(user.getUserName());
			Team team = teamService.findById(user.getTeamId());
			todo.setTeamName(team.getTeamName());
		}
		
		List<Todo> doneList = todoService.selectComplete(account.getTeamId());
		for (Todo todo:doneList) {
			long userId = todo.getUserId();
			User user = loginService.findById(userId);
			Team team = teamService.findById(user.getTeamId());
			todo.setUserName(user.getUserName());
			todo.setTeamName(team.getTeamName());
		}
		model.addAttribute("todos",list);
		model.addAttribute("doneTodos",doneList);
		model.addAttribute("todoForm",todoForm);
		model.addAttribute("account",account);
		return "index";
	}

	@PostMapping("/add")
	public String add(
			@Validated TodoForm todoForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			redirectAttribute.addFlashAttribute(todoForm);
			return "redirect:/todo/";
		}
		Todo todo = new Todo();
		todo.setTitle(todoForm.getTitle());
		todo.setTimeLimit(Date.valueOf(todoForm.getTimeLimit()));
		todo.setDoneFlag(false);
		todo.setUserId(account.getUserId());
		todo.setTeamId(account.getTeamId());
		todoService.add(todo);
		
		return "redirect:/todo/";
	}

	@PostMapping("/update")
	public String update(@Validated TodoForm todoForm) {

		Todo todo = new Todo();
		todo.setId(todoForm.getId());
		todo.setTitle(todoForm.getTitle());
		todo.setTimeLimit(Date.valueOf(todoForm.getTimeLimit()));
		Boolean doneFlag = todoForm.getDoneFlag();
		if (doneFlag == null) {
			doneFlag = false;
		}
		
		todo.setDoneFlag(doneFlag);
		todo.setUserId(todoForm.getUserId());
		todo.setTeamId(todoForm.getTeamId());
		todoService.update(todo);
		return "redirect:/todo/";
	}

	@PostMapping("/delete")
	public String delete() {
		todoService.delete();
		return "redirect:/todo/";
	}

}
