package com.example.hwh_community.post;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
public class PostUpForm {

    @NotBlank(message = "title 입력해주세요")
    public String title;
    @NotBlank(message = "content 입력해주세요")
    public String content;

}
