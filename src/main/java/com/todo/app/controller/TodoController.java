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

import com.todo.app.entity.Task;
import com.todo.app.entity.User;
import com.todo.app.form.TaskForm;
import com.todo.app.model.Account;
import com.todo.app.service.TaskService;
import com.todo.app.service.UserService;

@Controller
@RequestMapping("/todo")
public class TodoController {
	@Autowired
	private Account account;
	
	@Autowired
	private TaskService taskService;
	
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
    	List<Task> list = taskService.selectIncomplete(teamId);
		List<TaskForm> todos = new ArrayList<>();
		for (Task task:list) {
			TaskForm form = new TaskForm();
			form.setId(task.getId());
			form.setTaskContent(task.getTaskContent());
			form.setStatus(task.getStatus());
			form.setUserId(task.getUser().getId());
			form.setUserName(task.getUser().getUserName());
			form.setTeamName(task.getUser().getTeam().getTeamName());
			
			form.setDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			todos.add(form);
		}
		model.addAttribute("todos",todos);
		
		List<Task> doneList = taskService.selectComplete(teamId);
		List<TaskForm> doneTodos = new ArrayList<>();
		for (Task task:doneList) {
			TaskForm form = new TaskForm();
			form.setId(task.getId());
			form.setTaskContent(task.getTaskContent());
			form.setStatus(task.getStatus());
			form.setUserId(task.getUser().getId());
			form.setUserName(task.getUser().getUserName());
			form.setTeamName(task.getUser().getTeam().getTeamName());
			
			form.setDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			doneTodos.add(form);
		}
		
		model.addAttribute("doneTodos",doneTodos);
    }
    
	@GetMapping("/list")
	public String showListPage(TaskForm todoForm, Model model) {
		updateList(model);
		return "Todo-list";
	}

	@GetMapping("/add")
	public String showAddTaskForm(TaskForm todoForm, Model model) {
		return "Todo-add";
	}
	
	@PostMapping("/add")
	public String addTask(
			@Validated TaskForm taskForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			return "Todo-add";
		}
		Task task = new Task();
		task.setTaskContent(taskForm.getTaskContent());
		task.setDueDate(LocalDate.parse(taskForm.getDueDate()));
		task.setStatus(0);
		User user = userService.findById(account.getUserId()); 

		task.setUser(user);
		taskService.add(task);
		
		return "redirect:/todo/list";
	}

	@PostMapping("/update/{id}")
	public String updateTask(
			@PathVariable Long id, 
			@Validated TaskForm taskForm) {

		Task task = new Task();
		task.setId(id);
		task.setTaskContent(taskForm.getTaskContent());
		task.setDueDate(LocalDate.parse(taskForm.getDueDate()));
		
		task.setStatus(taskForm.getStatus());
		User user = userService.findById(account.getUserId()); 

		task.setUser(user);
		taskService.update(task);
		return "redirect:/todo/list";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskService.delete(id);
		return "redirect:/todo/list";
	}
}
