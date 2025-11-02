package com.todo.app.form;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TodoForm {
	public Long id;
	
    @Size(min = 1, max = 200, message = "{0}は{1}文字以上{2}文字以下で入力してください。")
	public String taskContent;
	
	public Integer status;
	
	@NotEmpty
	public String timeLimit;
	
	private Long userId;
	private String userName;
	
	private Long teamId;
	private String teamName;
}
