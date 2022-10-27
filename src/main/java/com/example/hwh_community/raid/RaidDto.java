package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class RaidDto {


    @NotBlank
    @Length(min = 2,max = 20)
    private String title;

    @NotBlank
    @Length(min = 2,max = 100)
    private String shortDescription;

    private Set<Account> members = new HashSet<>();

    private String fullDescription;

    private LocalDateTime publishedDateTime;

    private LocalDateTime closedDateTime;

    private LocalDateTime recruitingUpdatedDateTime;

    private boolean recruiting;

    private boolean published;

    private boolean closed;

    private boolean useBanner;

    private String tag;

    private Long maximum;

}
