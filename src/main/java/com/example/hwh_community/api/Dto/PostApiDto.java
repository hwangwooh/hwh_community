package com.example.hwh_community.api.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class PostApiDto {


    private Long id;

    private String title;

    private String content;

    private LocalDate dateTime;

    private Long countVisit = 0L;

    private boolean notice;

    private List<CommentApiDto> commentApiDtos;

    private List<AccontApiDto> accontApiDtos;


}
