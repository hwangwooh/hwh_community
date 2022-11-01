package com.example.hwh_community.post;

import com.example.hwh_community.api.AccountApiController;
import com.example.hwh_community.api.Dto.AccontApiDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
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
