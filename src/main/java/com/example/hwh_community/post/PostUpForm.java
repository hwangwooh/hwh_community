package com.example.hwh_community.post;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostUpForm {

    private String title;

    @Lob
    private String content;
}
