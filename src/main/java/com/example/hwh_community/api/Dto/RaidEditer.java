package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Account;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class RaidEditer {


    @NotBlank
    @Length(min = 2,max = 20)
    private String title;

    @NotBlank
    @Length(min = 2,max = 100)
    private String shortDescription;


    private String tag;

    private Long maximum;
}
