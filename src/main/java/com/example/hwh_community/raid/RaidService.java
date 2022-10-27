package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RaidService {

    private final RaidRepository raidRepository;

    private final ModelMapper modelMapper;


    public Raid newraid(Account account, RaidDto raidDto) {

        Raid raid = Raid.builder().account(account)
                .members(new HashSet<>())
                .title(raidDto.getTitle())
                .shortDescription(raidDto.getShortDescription().replace("\r\n", "<br>"))
                .publishedDateTime(LocalDateTime.now())
                .maximum(raidDto.getMaximum())
                .published(true)
                .tag(raidDto.getTag()).build();
        raid.addMemeber(account);
        Raid save = raidRepository.save(raid);
        return save;
    }

    @Transactional
    public void raindset(Long id,RaidDto raidDto) {

        Raid raid = raidRepository.findById(id).get();
        raid.setTitle(raidDto.getTitle());
        raid.setShortDescription(raidDto.getShortDescription().replace("\r\n","<br>"));
        raid.setTag(raidDto.getTag());
        raid.setMaximum(raidDto.getMaximum());

    }
}
