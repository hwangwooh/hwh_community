package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.raid.Gametype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RaidApiDto {

    private Long id;

    private AccountApiDto account;

    private List<AccountApiDto> members;

    private String path;

    private String title;

    private String shortDescription;


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

    private Gametype gametype;

    public RaidApiDto(Raid raid) {
        this.id = raid.getId();
        this.account = new AccountApiDto(raid.getAccount());
        this.members = raid.getMembers().stream().map(m -> new AccountApiDto(m)).collect(Collectors.toList());
        this.path = raid.getPath();
        this.title = raid.getTitle();
        this.shortDescription = raid.getShortDescription();
        this.fullDescription = raid.getFullDescription();
        this.publishedDateTime = raid.getPublishedDateTime();
        this.closedDateTime = raid.getClosedDateTime();
        this.recruitingUpdatedDateTime = raid.getRecruitingUpdatedDateTime();
        this.recruiting = raid.isRecruiting();
        this.published = raid.isPublished();
        this.closed = raid.isClosed();
        this.useBanner = raid.isUseBanner();
        this.tag = raid.getTag();
        this.maximum = raid.getMaximum();
        this.gametype = raid.getGametype();
    }
}
