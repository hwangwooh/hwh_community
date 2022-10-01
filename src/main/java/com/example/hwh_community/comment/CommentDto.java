package com.example.hwh_community.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {

    private Long id;
    @Lob
    private String comment;

    private LocalDate dateTime;

    private String nickname;
}
