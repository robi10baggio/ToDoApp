package com.todo.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.form.TodoForm;
import com.todo.app.model.Account;
import com.todo.app.service.TodoService;
import com.todo.app.service.UserService;

@Controller
@RequestMapping("/todo")
public class TodoController {
	@Autowired
	private Account account;
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserService userService;


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
    	User user = userService.findById(account.getUserId());
    	Long teamId = user.getTeam().getId();
    	List<Todo> list = todoService.selectIncomplete(teamId);
		List<TodoForm> todos = new ArrayList<>();
		for (Todo todo:list) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			todos.add(form);
		}
		model.addAttribute("todos",todos);
		
		List<Todo> doneList = todoService.selectComplete(teamId);
		List<TodoForm> doneTodos = new ArrayList<>();
		for (Todo todo:doneList) {
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			doneTodos.add(form);
		}
		
		model.addAttribute("doneTodos",doneTodos);
    }
    
	@GetMapping("/list")
	public String list(TodoForm todoForm, Model model) {
		updateList(model);
		return "Todo-list";
	}

	@GetMapping("/add")
	public String addForm(TodoForm todoForm, Model model) {
		return "Todo-add";
	}
	
	@PostMapping("/add")
	public String add(
			@Validated TodoForm todoForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			return "Todo-add";
		}
		Todo todo = new Todo();
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		todo.setStatus(0);
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.add(todo);
		
		return "redirect:/todo/list";
	}

	@PostMapping("/update/{id}")
	public String update(
			@PathVariable Long id, 
			@Validated TodoForm todoForm) {

		Todo todo = new Todo();
		todo.setId(id);
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		
		todo.setStatus(todoForm.getStatus());
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.update(todo);
		return "redirect:/todo/list";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		todoService.delete(id);
		return "redirect:/todo/list";
	}
}
