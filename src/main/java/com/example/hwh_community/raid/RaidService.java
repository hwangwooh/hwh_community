package com.example.hwh_community.raid;

import com.example.hwh_community.api.Dto.RaidApiDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<RaidApiDto> raidlist(RaidSearch raidSearch) {

      return raidRepository.getList(raidSearch).stream().map(raid -> new RaidApiDto(raid)).collect(Collectors.toList());
    }

    public List<RaidApiDto> raidlisttag(RaidSearch raidSearch,String tag) {

        return raidRepository.getListtag(raidSearch,tag).stream().map(raid -> new RaidApiDto(raid)).collect(Collectors.toList());
    }

    public List<RaidApiDto> raidlistme(Account account, RaidSearch raidSearch) {
        return raidRepository.getListme(raidSearch, account).stream().map(raid -> new RaidApiDto(raid)).collect(Collectors.toList());

    }
}
