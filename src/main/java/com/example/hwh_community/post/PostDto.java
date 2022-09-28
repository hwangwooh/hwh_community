package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDate;

@Getter
@Setter
public class PostDto {

    private Long id;

    private String title;

    @Lob
    private String content;


    private LocalDate dateTime;

    private String nickname;


    public PostDto(Post post) {


        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.dateTime = post.getDateTime();
        this.nickname = post.getAccount().getNickname();

    }


    @Builder
    public PostDto(Long id, String title, String content,LocalDate dateTime,String nickname) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10));
        this.content = content;
        this.dateTime = dateTime;
        this.nickname = nickname;
    }
}
