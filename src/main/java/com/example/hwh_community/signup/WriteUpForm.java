package com.example.hwh_community.signup;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WriteUpForm {

    @NotBlank(message = "title 입력해주세요")
    public String title;
    @NotBlank(message = "content 입력해주세요")
    public String content;

}
