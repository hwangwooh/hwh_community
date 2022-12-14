package com.example.hwh_community.raid;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.comment.CommentDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;


import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequiredArgsConstructor
@Slf4j
public class RaidController {

    private final ModelMapper modelMapper;
    private final RaidRepository raidRepository;

    private final AccountRepository accountRepository;

    private final RaidService raidService;


    @GetMapping("raid/new-raid")
    public String newraidForm(@CurrentAccount Account account, Model model) {


        model.addAttribute(account);
        model.addAttribute(new RaidDto());

        return "raid/new-raid";
    }

    @GetMapping("raid/new-raid/map")
    public String newraidmapForm(@CurrentAccount Account account, Model model) {


        model.addAttribute(account);
        model.addAttribute(new RaidDto());

        return "raid/new-raid-map";
    }

    @PostMapping("raid/new-raid")
    public String postraid(@CurrentAccount Account account, @Valid RaidDto raidDto,Errors errors) {

        if (errors.hasErrors()) {
            return "raid/new-raid";
        }

        Raid raid = raidService.newraid(account, raidDto);
        return "redirect:/raid/raid-hom/"+raid.getId();
    }

    @PostMapping("raid/new-raid/map")
    public String postraidmap(@CurrentAccount Account account, @Valid RaidDto raidDto,Errors errors) {

        if (errors.hasErrors()) {
            return "raid/new-raid-map";
        }
        Raid raid = raidService.newraidmap(account, raidDto);
        return "redirect:/raid/raid-hom/"+raid.getId();
    }



    @GetMapping("raid/list-raid-tag")///raid/list-raid/new?tag=??????&gametype=map
    public String getlistraid_lost(String tag, Model model,@CurrentAccount Account account,
                              @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                              Pageable pageable) {
        Page<Raid> raids;
        Gametype gametype1 = Gametype.LOST;

        raids = raidRepository.findAllByGametypeAndTag(gametype1,tag, pageable);

        model.addAttribute("tag", tag);
        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid";


    }

    @GetMapping("raid/list-raid")///raid/list-raid/new?tag=??????&gametype=map
    public String getlistraid_lost_not_tag(Model model,@CurrentAccount Account account,
                                   @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        Page<Raid> raids;
        Gametype gametype1 = Gametype.LOST;

        raids = raidRepository.findAllByGametype(gametype1,pageable);

        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid2";
    }

    @GetMapping("raid/list-raid-tag-map")///raid/list-raid/new?tag=??????&gametype=map
    public String getlistraid_map(String tag, Model model,@CurrentAccount Account account,
                               @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                               Pageable pageable) {
        Page<Raid> raids;
        Gametype gametype1 = Gametype.MAP;

        raids = raidRepository.findAllByGametypeAndTag(gametype1,tag, pageable);

        model.addAttribute("tag", tag);
        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid-map";


    }

    @GetMapping("raid/list-raid-map")///raid/list-raid/new?tag=??????&gametype=map
    public String getlistraid_map_not_tag(Model model,@CurrentAccount Account account,
                                  @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                                  Pageable pageable) {
        Page<Raid> raids;
        Gametype gametype1 = Gametype.MAP;

        raids = raidRepository.findAllByGametype(gametype1,pageable);

        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid-map";

    }

    @GetMapping("raid/list-raid/me")
    public String getlistraidme_lost(Model model,@CurrentAccount Account account,
                              @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                              Pageable pageable) {

        Gametype gametype1 = Gametype.LOST;
        Page<Raid> raids = raidRepository.findAllBymembersAndGametype(account,gametype1,pageable);
        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid-me";
    }

    @GetMapping("raid/list-raid/me-map")
    public String getlistraidme_map(Model model,@CurrentAccount Account account,
                                @PageableDefault(size = 12, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                                Pageable pageable) {
        Gametype gametype1 = Gametype.MAP;
        Page<Raid> raids = raidRepository.findAllBymembersAndGametype(account,gametype1,pageable);

        model.addAttribute("raids", raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "raid/list-raid-me-map";

    }


    @GetMapping("raid/raid-hom/{id}")
    public String getraidhom(@PathVariable("id") Long id, Model model) {

        Raid raid = raidRepository.findById(id).get();
        model.addAttribute("raid",raid);
        return "raid/raid-hom";

    }


    @GetMapping("raid/raid-hom/{id}/add/{member}") // ??????
    public String postaddmembers(@PathVariable("id") Long id, @PathVariable("member") String member, RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).get();
        if(raid.getMembers().size() >= raid.getMaximum()){
            attributes.addFlashAttribute("message", "?????? ?????? ?????????.");
            return  "redirect:/raid/raid-hom/"+id;
        }

        if(!raid.isPublished()){
            attributes.addFlashAttribute("message", "???????????? ???????????????.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        raidService.addmember(raid, member);
        return "redirect:/raid/raid-hom/"+id;
    }
    @GetMapping("raid/raid-hom/{id}/remove/{member}") // ??????
    public String postremovemembers(@PathVariable("id") Long id, @PathVariable("member") String member, Model model)
    {
        raidService.removemember(id, member);
        return  "redirect:/raid/raid-hom/"+id;
    }



    @GetMapping("raid/raid-hom/delete/{id}") // ????????? ??????
    public String raiddelete(@CurrentAccount Account account,@PathVariable("id") Long id) {

        boolean raiddelete = raidService.raiddelete(id, account);
        if (!raiddelete){
            return "index";
        }
        return "redirect:/raid/list-raid";
    }

    @GetMapping("raid/raid-hom/delete-map/{id}") // ????????? ??????
    public String raiddelete_map(@CurrentAccount Account account,@PathVariable("id") Long id) {
        boolean raiddelete = raidService.raiddelete(id, account);
        if (!raiddelete){
            return "index";
        }
        return "redirect:/raid/list-raid-map";
    }





    @GetMapping("raid/membersset/{id}")
    public String getmembersset(@PathVariable("id") Long id,@CurrentAccount Account account, Model model,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();
        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "?????? ???????????? ???????????? ????????????.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        model.addAttribute("raid",raid);
        return "raid/raid-members";
    }

    @GetMapping("raid/memberssetdelete/{raidid}/{id}")
    public String getmemberssetdelete(@CurrentAccount Account account,@PathVariable("raidid") Long raidid,@PathVariable("id") Long memderid, Model model,RedirectAttributes attributes) {

        Raid raid = raidService.memberdelete(account, raidid, memderid);

        model.addAttribute("raid",raid);
        return "raid/raid-members";
    }

    @GetMapping("raid/raidset/{id}")
    public String getraidset(@PathVariable("id") Long id,@CurrentAccount Account account, Model model,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();
        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "?????? ???????????? ???????????? ????????????.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        raid.setShortDescription(raid.getShortDescription().replace("<br>","\r\n"));

        model.addAttribute(new RaidDto());
        model.addAttribute("raid",raid);

        return "raid/raid-setting";
    }

    @GetMapping("raid/raidset-map/{id}")
    public String getraidset_map(@PathVariable("id") Long id,@CurrentAccount Account account, Model model,RedirectAttributes attributes) {

        Raid raid = raidRepository.findById(id).orElseThrow();
        if(!raid.getAccount().getNickname().equals(account.getNickname())){
            attributes.addFlashAttribute("message", "?????? ???????????? ???????????? ????????????.");
            return  "redirect:/raid/raid-hom/"+id;
        }
        raid.setShortDescription(raid.getShortDescription().replace("<br>","\r\n"));

        model.addAttribute(new RaidDto());
        model.addAttribute("raid",raid);

        return "raid/raid-setting-map";
    }


    @PostMapping("raid/raidset/{id}")
    public String postraidset(@PathVariable("id") Long id, @Valid RaidDto raidDto) {
       raidService.raindset(id,raidDto);
        return "redirect:/raid/raid-hom/"+id;
    }
    @PostMapping("raid/raidset-map/{id}")
    public String postraidset_map(@PathVariable("id") Long id, @Valid RaidDto raidDto) {
        raidService.raindset(id,raidDto);
        return "redirect:/raid/raid-hom/"+id;
    }

    @GetMapping("raid/profile/{nickname}")
    public String getraidprofile(@PathVariable("nickname") String nickname, Model model
            ,RedirectAttributes attributes) {

        Account account = accountRepository.findByNickname(nickname);


        model.addAttribute("account",account);

        return "raid/profile";
    }

    @GetMapping("raid/raid-hom/published/{id}")
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
