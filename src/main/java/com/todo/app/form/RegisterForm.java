package com.todo.app.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {
	@NotEmpty
	@Size(max = 40, message = "{0}は{1}文字以上で入力してください。")
	@Email(message = "{0}はメールアドレスの形式で入力してください。")
	private String userId;
	
	@NotEmpty
	@Size(max = 40, message = "{0}は{1}文字以上で入力してください。")
	private String userName;
	
	@Size(min = 5, max = 40, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String password;
	
	private Integer teamId;
	
	@Size(min = 5, max = 40, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	private String checkPassword;
}
