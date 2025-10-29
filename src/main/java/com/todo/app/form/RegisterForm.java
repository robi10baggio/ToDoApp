package com.todo.app.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {
	@NotEmpty
	private String userId;
	
	@NotEmpty
	@Size(min = 2, max = 20, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String userName;
	
	@NotEmpty
	@Size(min = 5, max = 20, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String password;
	
	private Integer teamId;
	
	@NotEmpty
	@Size(min = 5, max = 20, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String checkPassword;
}
