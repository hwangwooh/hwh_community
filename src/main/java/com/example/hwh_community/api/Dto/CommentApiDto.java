package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Comment;
import lombok.Data;

@Data
public class CommentApiDto {

    private Long id;

    private String comment;

    private AccountApiDto account;

    public CommentApiDto(Comment c) {
        this.id = c.getId();
        this.comment = c.getComment();
        this.account = new AccountApiDto(c.getAccount());
    }
}
