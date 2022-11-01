package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import lombok.Data;

import javax.persistence.Lob;
import java.util.List;
@Data
public class CommentApiDto {

    private Long id;

    private String comment;

    private AccontApiDto account;

    public CommentApiDto(Comment c) {
        this.id = c.getId();
        this.comment = c.getComment();
        this.account = new AccontApiDto(c.getAccount());
    }
}
