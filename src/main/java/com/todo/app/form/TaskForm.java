package com.todo.app.form;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskForm {
	public Long id;
	
    @Size(max = 200, min = 1,message = "{0}は{1}文字以上{2}文字以下で入力してください。")
    private String taskContent;
	
    private Integer status;
	
	@NotEmpty
	private String dueDate;
	
	private Long userId;
	private String userName;
	
	private String teamName;
}
