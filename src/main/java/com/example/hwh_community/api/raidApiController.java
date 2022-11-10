package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.api.Dto.AccountApiDto;
import com.example.hwh_community.api.Dto.RaidApiDto;
import com.example.hwh_community.api.Dto.RaidEditer;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.raid.RaidDto;
import com.example.hwh_community.raid.RaidRepository;
import com.example.hwh_community.raid.RaidSearch;
import com.example.hwh_community.raid.RaidService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class raidApiController {

    private final ModelMapper modelMapper;
    private final RaidRepository raidRepository;

    private final AccountRepository accountRepository;

    private final RaidService raidService;


    @PostMapping("raid/api/new-raid")
    public void postraid(@CurrentAccount Account account, @RequestBody @Valid RaidEditer raidEditer) {
        raidService.apinewraid(account, raidEditer);

    }

    @GetMapping("raid/api/list-raid1/{tag}")
    public List<RaidApiDto> getlistraid2(@PathVariable("tag") String tag
            ,@CurrentAccount Account account
            ,@ModelAttribute RaidSearch raidSearch)
    {
        List<RaidApiDto> raidApiDtoList = raidService.raidlist(raidSearch);
        return raidApiDtoList;
    }

    @GetMapping("raid/api/list-raid2/{tag}")
    public List<RaidApiDto> getlistraid(@PathVariable("tag") String tag
            ,@CurrentAccount Account account
            ,@ModelAttribute RaidSearch raidSearch)
    {
        List<RaidApiDto> raidApiDtoList = raidService.raidlisttag(raidSearch,tag);
        return raidApiDtoList;
    }

    @GetMapping("raid/api/list-raid/me")
    public List<RaidApiDto> getlistraidme(@CurrentAccount Account account
            , @ModelAttribute RaidSearch raidSearch)
    {
        List<RaidApiDto> raidApiDtoList = raidService.raidlistme(account,raidSearch);
        return raidApiDtoList;
    }


    @GetMapping("raid/api/raid-hom/{id}")
    public RaidApiDto getraidhom(@PathVariable("id") Long id)
    {
        Raid raid = raidRepository.findById(id).get();
        return new RaidApiDto(raid);
    }
    @PostMapping("raid/api/raid-hom/{id}/add/{member}") // 참가
    public void postaddmembers(@PathVariable("id") Long id,
                                 @PathVariable("member") String member
            ,@CurrentAccount Account account, RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).get();

        if(raid.getMembers().size() >= raid.getMaximum()){
            //메시지 전달
            attributes.addFlashAttribute("message", "인원 초과 입니다.");

        }

        if(!raid.isPublished()){
            attributes.addFlashAttribute("message", "모집완료 되었습니다.");

        }

        Account byNickname = accountRepository.findByNickname(member);
        if(account.equals(byNickname)){
            raid.inMemeber(byNickname);
        }else // 메시지 ?


        raidRepository.save(raid);
    }
    @PostMapping("raid/api/raid-hom/{id}/remove/{member}") // 탈퇴
    public void postremovemembers(@PathVariable("id") Long id, @PathVariable("member") String member,@CurrentAccount Account account, Model model) {


        Raid raid = raidRepository.findById(id).get();

        Account byNickname = accountRepository.findByNickname(member);
        if(account.equals(byNickname)){
            raid.removeMember(byNickname);
            raidRepository.save(raid);
        }
        else{

        }


    }

    @DeleteMapping ("raid/api/raid-hom/delete/{id}") // 레이드 삭제
    public void raiddelete(@PathVariable("id") Long id,@CurrentAccount Account account) {


        Raid raid = raidRepository.findById(id).get();
        if (raid.getAccount().equals(account)) {
            raidRepository.delete(raid);
        } else {
            // 예정
        }


    }


    @GetMapping("raid/api/membersset/{id}")
    public RaidApiDto getmembersset(@PathVariable("id") Long id,@CurrentAccount Account account
            ,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();


        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "해당 레이드에 공대장이 아닙니다.");

        }

        return new RaidApiDto(raid);
    }

    @PostMapping ("raid/api/memberssetdelete/{raidid}/{id}")// 레이드 강퇴
    public RaidApiDto getmemberssetdelete(@PathVariable("raidid") Long raidid,@PathVariable("id") Long id
            ,RedirectAttributes attributes) {
        Raid raid = raidRepository.findById(raidid).get();
        Account account1 = accountRepository.findById(id).get();
        raid.removeMember(account1);
        raidRepository.save(raid);
        return new RaidApiDto(raid);
    }

    @GetMapping("raid/api/raidset/{id}")// 레이드 수정
    public RaidApiDto getraidset(@PathVariable("id") Long id,@CurrentAccount Account account
            ,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();
        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "해당 레이드에 공대장이 아닙니다.");
        }
        raid.setShortDescription(raid.getShortDescription().replace("<br>","\r\n"));

        return new RaidApiDto(raid);
    }


    @PostMapping("raid/api/raidset/{id}")//레이드 수정
    public void postraidset(@PathVariable("id") Long id, @Valid RaidDto raidDto) {


        raidService.raindset(id,raidDto);


    }

    @GetMapping("raid/api/profile/{nickname}")// 프로필 조회
    public AccountApiDto getraidprofile(@PathVariable("nickname") String nickname, Model model
            , RedirectAttributes attributes) {

        Account account = accountRepository.findByNickname(nickname);


        model.addAttribute("account",account);

        return new AccountApiDto(account);
    }

    @PostMapping ("raid/api/raid-hom/published/{id}") // 모집 활성
    public void getpublished(@PathVariable("id") Long id){

        Raid raid = raidRepository.findById(id).get();

        if(raid.isPublished()){
            raid.setPublished(false);
            raidRepository.save(raid);

        }else{

            raid.setPublished(true);
            raidRepository.save(raid);
        }

    }

}
