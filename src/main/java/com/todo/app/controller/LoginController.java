package com.todo.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Team;
import com.todo.app.entity.User;
import com.todo.app.form.LoginForm;
import com.todo.app.model.Account;
import com.todo.app.service.LoginService;
import com.todo.app.service.TeamService;

@Controller
public class LoginController {

	@Autowired
	HttpSession session;

	@Autowired
	Account account;
	
	@Autowired
	LoginService loginService;
	
	@Autowired 
	TeamService teamService;


	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();
		// エラーパラメータのチェック
		if (error.equals("notLoggedIn")) {
			model.addAttribute("message", "ログインしてください");
		}

		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@Validated LoginForm loginForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {
		if (bindingResult.hasErrors()) {
			redirectAttribute.addFlashAttribute(loginForm);
			return "redirect:/";
		}
		User user = loginService.loginByAccount(loginForm.getUserId(), loginForm.getPassword());
		
		if (user == null) {
			return "redirect:/login";
		}
		// セッション管理されたアカウント情報に名前をセット
		account.setUserId(user.getId());
		account.setUserName(user.getUserName());
		account.setTeamId(user.getTeam().getId());
		Team team = teamService.findById(user.getTeam().getId());
		account.setTeamName(team.getTeamName());
		

		// 「/todo」へのリダイレクト
		return "redirect:/todo/";
	}
}
