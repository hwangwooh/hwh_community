package com.example.hwh_community.api.Dto;

import lombok.Data;

import javax.persistence.Lob;
import java.util.List;
@Data
public class CommentApiDto {

    private Long id;

    private String comment;

    private List<AccontApiDto> accontApiDtos;
}
