package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Post;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class PostApiDto {


    private Long id;
    @NotBlank(message = "title 입력해주세요")
    private String title;
    @NotBlank(message = "content 입력해주세요")
    private String content;

    private LocalDate dateTime;

    private Long countVisit = 0L;

    private boolean notice;

    private List<CommentApiDto> commentList;

    private AccountApiDto account;


    public PostApiDto(Post p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.dateTime = p.getDateTime();
        this.countVisit = p.getCountVisit();
        this.notice = p.isNotice();
        this.commentList = p.getCommentList().stream()
                    .map(c ->new CommentApiDto(c)).collect(Collectors.toList());


        this.account = new AccountApiDto(p.getAccount());
    }

}
