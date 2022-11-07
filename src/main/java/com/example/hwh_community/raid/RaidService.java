package com.example.hwh_community.raid;

import com.example.hwh_community.account.AccountRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RaidService {

    private final RaidRepository raidRepository;

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

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

    public void removemember(Long id, String member) {

        Raid raid = raidRepository.findById(id).get();
        Account byNickname = accountRepository.findByNickname(member);
        raid.removeMember(byNickname);
       // raidRepository.save(raid);
    }

    public boolean raiddelete(Long id, Account account) {

        Raid raid = raidRepository.findById(id).get();
        if(raid.getAccount() == account){
            raidRepository.delete(raid);
            return true;
        } else return false;

    }

    public Raid memberdelete(Account account, Long raidid, Long memderid) {

        Raid raid = raidRepository.findById(raidid).get();
        Account account1 = accountRepository.findById(memderid).get();
        raid.removeMember(account1);

        return raid; // 수정 예정
    }

    public void addmember(Raid raid, String member) {
        Account byNickname = accountRepository.findByNickname(member);
        raid.addMemeber(byNickname);
    }
}
