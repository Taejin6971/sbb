package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {

	@NotEmpty(message = "답변은 비어있을 수 없습니다.")
	private String content;
}
