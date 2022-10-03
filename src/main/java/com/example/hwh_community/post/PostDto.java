package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    @Lob
    private String content;


    private LocalDate dateTime;

    private String nickname;



}
