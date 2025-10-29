package com.todo.app.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	private static Map<Integer, String> statusMenumap = new HashMap<>();
    static {
    	statusMenumap.put(0, "未着手");
    	statusMenumap.put(1, "実施中");
    	statusMenumap.put(2, "完了");
    }
    
    @ModelAttribute("statusMenu")
    public static  Map<Integer, String> getStatusMenu() {
		return TodoController.statusMenumap;
    }
    
    @ModelAttribute("account")
    public Account getAccount() {
		return this.account;
    }
    
	@GetMapping("/")
	public String index(TodoForm todoForm, Model model) {
		
		List<Todo> list = todoService.selectIncomplete(account.getTeamId());
		
		
		List<Todo> doneList = todoService.selectComplete(account.getTeamId());

		model.addAttribute("todos",list);
		model.addAttribute("doneTodos",doneList);
		model.addAttribute("todoForm",todoForm);
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
		todo.setStatus(0);
		User user = loginService.findById(account.getUserId()); 
		Team team = teamService.findById(account.getTeamId());

		todo.setUser(user);
		todo.setTeam(team);
		todoService.add(todo);
		
		return "redirect:/todo/";
	}

	@PostMapping("/update")
	public String update(@Validated TodoForm todoForm) {

		Todo todo = new Todo();
		todo.setId(todoForm.getId());
		todo.setTitle(todoForm.getTitle());
		todo.setTimeLimit(Date.valueOf(todoForm.getTimeLimit()));
		
		todo.setStatus(todoForm.getStatus());
		User user = loginService.findById(account.getUserId()); 
		Team team = teamService.findById(account.getTeamId());

		todo.setUser(user);
		todo.setTeam(team);
		todoService.update(todo);
		return "redirect:/todo/";
	}

	@PostMapping("/delete")
	public String delete() {
		todoService.delete();
		return "redirect:/todo/";
	}

}
