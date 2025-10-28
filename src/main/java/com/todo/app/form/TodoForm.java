package com.todo.app.form;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TodoForm {
	public Integer id;
	
	@NotEmpty(message = "{0}が未入力です。")
    @Size(min = 2, max = 14, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	public String title;
	
	public Boolean doneFlag;
	
	@NotEmpty(message = "{0}が未入力です。")
	public String timeLimit;
	
	private long userId;
	
	private long teamId;
}
