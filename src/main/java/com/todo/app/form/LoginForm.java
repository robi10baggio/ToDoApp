package com.todo.app.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	@NotEmpty
	private String userId;
	
	@Size(min = 2, max = 20, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String password;
}
