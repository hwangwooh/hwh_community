package com.example.hwh_community.post;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    @NotBlank
    @Length(min = 3,max = 20)
    public String title;
    @NotBlank
    @Length(min = 3,max = 300)
    public String content;


    private LocalDate dateTime;

    private String nickname;

    private Long countVisit;

}
