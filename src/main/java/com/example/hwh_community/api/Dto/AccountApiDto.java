package com.example.hwh_community.api.Dto;

import com.example.hwh_community.domain.Account;
import lombok.Data;

@Data
public class AccountApiDto {

    private String nickname;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    private String profileImage;

    public AccountApiDto(Account account) {
        this.nickname = account.getNickname();
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
    }
}
