package com.example.hwh_community.api.Dto;

import com.example.hwh_community.api.AccountApiController;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class PostApiDto {


    private Long id;

    private String title;

    private String content;



    private LocalDate dateTime;

    private Long countVisit = 0L;

    private boolean notice;

    private List<CommentApiDto> commentList;

    private AccontApiDto account;


    public PostApiDto(Post p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.dateTime = p.getDateTime();
        this.countVisit = p.getCountVisit();
        this.notice = p.isNotice();
        this.commentList = p.getCommentList().stream()
                .map(c ->new CommentApiDto(c)).collect(Collectors.toList());
//        this.commentList = p.getCommentList();
        this.account = new AccontApiDto(p.getAccount());
    }

}
