package com.example.hwh_community.account;

import com.example.hwh_community.api.Dto.CommentApiDto;
import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.api.Dto.RaidApiDto;
import com.example.hwh_community.domain.Account;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class AccountDto {

    private String nickname;

    private String bio;

    private String url;

    private String occupation;

    private String location;


    private List<PostApiDto> postList;


    private List<RaidApiDto> raid_ctor;


    private List<CommentApiDto> comments;
    
    private List<RaidApiDto> raid_members;
    private String profileImage;

    public AccountDto(Account account) {
        this.nickname = account.getNickname();
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
//        this.postList = account.getPostList().stream().map(post -> new PostApiDto(post)).collect(Collectors.toList());
//        this.comments = account.getComments().stream().map(comment -> new CommentApiDto(comment)).collect(Collectors.toList());
//        this.raid_ctor = account.getRaid_account().stream().map(raid -> new RaidApiDto(raid)).collect(Collectors.toList());
//        this.raid_members = account.getRaid_memders().stream().map(raid -> new RaidApiDto(raid)).collect(Collectors.toList());
    }
}
