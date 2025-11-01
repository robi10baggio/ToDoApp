package com.todo.app.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Team;
import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.form.TodoForm;
import com.todo.app.model.Account;
import com.todo.app.service.TeamService;
import com.todo.app.service.TodoService;
import com.todo.app.service.UserService;

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
	UserService userService;
	
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
    
    private void updateList(Model model) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	List<Todo> list = todoService.selectIncomplete(account.getTeamId());
		List<TodoForm> forms = new ArrayList<>();
		for (Todo todo:list) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTitle(todo.getTitle());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamId(todo.getTeam().getId());
			form.setTeamName(todo.getTeam().getTeamName());
			
			form.setTimeLimit(dateFormat.format(todo.getTimeLimit()));
			forms.add(form);
		}
		model.addAttribute("todos",forms);
		
		List<Todo> doneList = todoService.selectComplete(account.getTeamId());
		List<TodoForm> doneForms = new ArrayList<>();
		for (Todo todo:doneList) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTitle(todo.getTitle());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamId(todo.getTeam().getId());
			form.setTeamName(todo.getTeam().getTeamName());
			
			form.setTimeLimit(dateFormat.format(todo.getTimeLimit()));
			doneForms.add(form);
		}
		
		model.addAttribute("doneTodos",doneForms);
    }
    
	@GetMapping("/list")
	public String list(TodoForm todoForm, Model model) {
		updateList(model);
		return "Todo-list";
	}

	@PostMapping("/add")
	public String add(
			@Validated TodoForm todoForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			updateList(model);
			return "Todo-list";
		}
		Todo todo = new Todo();
		todo.setTitle(todoForm.getTitle());
		todo.setTimeLimit(Date.valueOf(todoForm.getTimeLimit()));
		todo.setStatus(0);
		User user = userService.findById(account.getUserId()); 
		Team team = teamService.findById(account.getTeamId());

		todo.setUser(user);
		todo.setTeam(team);
		todoService.add(todo);
		
		return "redirect:/todo/list";
	}

	@PostMapping("/update/{id}")
	public String update(
			@PathVariable Long id, 
			@Validated TodoForm todoForm) {

		Todo todo = new Todo();
		todo.setId(id);
		todo.setTitle(todoForm.getTitle());
		todo.setTimeLimit(Date.valueOf(todoForm.getTimeLimit()));
		
		todo.setStatus(todoForm.getStatus());
		User user = userService.findById(account.getUserId()); 
		Team team = teamService.findById(account.getTeamId());

		todo.setUser(user);
		todo.setTeam(team);
		todoService.update(todo);
		return "redirect:/todo/list";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		todoService.delete(id);
		return "redirect:/todo/list";
	}
}
