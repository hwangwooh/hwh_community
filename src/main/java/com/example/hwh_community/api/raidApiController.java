package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.post.PostSearch;
import com.example.hwh_community.raid.RaidDto;
import com.example.hwh_community.raid.RaidRepository;
import com.example.hwh_community.raid.RaidSearch;
import com.example.hwh_community.raid.RaidService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class raidApiController {

    private final ModelMapper modelMapper;
    private final RaidRepository raidRepository;

    private final AccountRepository accountRepository;

    private final RaidService raidService;


    @PostMapping("raid/api/new-raid")
    public void postraid(@CurrentAccount Account account, @RequestBody @Valid RaidDto raidDto) {


        Raid raid = raidService.newraid(account, raidDto);

    }

    @GetMapping("raid/api/list-raid")
    public String getlistraid2(@PathVariable("tag") String tag, Model model, @CurrentAccount Account account,@ModelAttribute RaidSearch raidSearch)
    {

        return "null";
    }

    @GetMapping("raid/api/list-raid")
    public String getlistraid(@PathVariable("tag") String tag, Model model, @CurrentAccount Account account
            , @ModelAttribute RaidSearch raidSearch) {

        return "null";

    }

    @GetMapping("raid/api/list-raid/me")
    public String getlistraidme(String tag, Model model,@CurrentAccount Account account,
                                @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                                Pageable pageable) {

        Page<Raid> raids = raidRepository.findBymembers(account,pageable);

        model.addAttribute("raids", raids);
        model.addAttribute("tag", tag);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid";
    }


    @GetMapping("raid/api/raid-hom/{id}")
    public String getraidhom(@PathVariable("id") Long id, Model model) {
        Raid raid = raidRepository.findById(id).get();
        model.addAttribute("raid",raid);
        return "raid/raid-hom";
    }
    @GetMapping("raid/api/raid-hom/{id}/add/{member}") // 참가
    public String postaddmembers(@PathVariable("id") Long id, @PathVariable("member") String member, RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).get();
        if(raid.getMembers().size() >= raid.getMaximum()){
            attributes.addFlashAttribute("message", "인원 초과 입니다.");
            return  "redirect:/raid/raid-hom/"+id;
        }

        if(!raid.isPublished()){
            attributes.addFlashAttribute("message", "모집완료 되었습니다.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        Account byNickname = accountRepository.findByNickname(member);
        raid.addMemeber(byNickname);
        raidRepository.save(raid);

        return "redirect:/raid/raid-hom/"+id;
    }
    @GetMapping("raid/api/raid-hom/{id}/remove/{member}") // 탈퇴
    public String postremovemembers(@PathVariable("id") Long id, @PathVariable("member") String member, Model model) {


        Raid raid = raidRepository.findById(id).get();

        Account byNickname = accountRepository.findByNickname(member);
        raid.removeMember(byNickname);
        raidRepository.save(raid);
        return  "redirect:/raid/raid-hom/"+id;
    }

    @GetMapping("raid/api/raid-hom/delete/{id}") // 레이드 삭제
    public String raiddelete(@PathVariable("id") Long id, Model model) {


        Raid raid = raidRepository.findById(id).get();

        raidRepository.delete(raid);

        return "redirect:/raid/list-raid";
    }


    @GetMapping("raid/api/membersset/{id}")
    public String getmembersset(@PathVariable("id") Long id,@CurrentAccount Account account, Model model
            ,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();

        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "해당 레이드에 공대장이 아닙니다.");
            return  "redirect:/raid/raid-hom/"+id;
        }


        model.addAttribute("raid",raid);

        return "raid/raid-members";
    }

    @GetMapping("raid/api/memberssetdelete/{raidid}/{id}")
    public String getmemberssetdelete(@PathVariable("raidid") Long raidid,@PathVariable("id") Long id, Model model
            ,RedirectAttributes attributes) {
        Raid raid = raidRepository.findById(raidid).get();
        Account account1 = accountRepository.findById(id).get();
        raid.removeMember(account1);
        raidRepository.save(raid);
        model.addAttribute("raid",raid);
        return "raid/raid-members";
    }

    @GetMapping("raid/api/raidset/{id}")
    public String getraidset(@PathVariable("id") Long id,@CurrentAccount Account account, Model model
            ,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();
        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "해당 레이드에 공대장이 아닙니다.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        raid.setShortDescription(raid.getShortDescription().replace("<br>","\r\n"));

        model.addAttribute(new RaidDto());
        model.addAttribute("raid",raid);

        return "raid/raid-setting";
    }


    @PostMapping("raid/api/raidset/{id}")
    public String postraidset(@PathVariable("id") Long id, @Valid RaidDto raidDto) {


        raidService.raindset(id,raidDto);


        return "redirect:/raid/raid-hom/"+id;
    }

    @GetMapping("raid/api/profile/{nickname}")
    public String getraidprofile(@PathVariable("nickname") String nickname, Model model
            ,RedirectAttributes attributes) {

        Account account = accountRepository.findByNickname(nickname);


        model.addAttribute("account",account);

        return "raid/profile";
    }

    @GetMapping("raid/api/raid-hom/published/{id}")
    public String getpublished(@PathVariable("id") Long id){

        Raid raid = raidRepository.findById(id).get();

        if(raid.isPublished()){
            raid.setPublished(false);
            raidRepository.save(raid);

        }else{

            raid.setPublished(true);
            raidRepository.save(raid);
        }



        return "redirect:/raid/raid-hom/"+id;
    }

}
