package com.example.hwh_community.signup;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class WriteUpForm {

    @NotBlank(message = "title 입력해주세요")
    @Length(min = 2,max = 20)
    public String title;
    @NotBlank(message = "content 입력해주세요")
    @Length(min = 2,max = 300)
    public String content;

}
