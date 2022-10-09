package com.example.hwh_community.raid;

import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class RaidController {

    private final ModelMapper modelMapper;
    private final RaidRepository raidRepository;




    @GetMapping("raid/new-raid")
    public String newraidForm(@CurrentAccount Account account, Model model) {


        model.addAttribute(account);
        model.addAttribute(new RaidDto());

        return "/raid/new-raid";
    }

    @PostMapping("raid/new-raid")
    public String postraid(@CurrentAccount Account account, @Valid RaidDto raidDto) {

        Raid raid = Raid.builder().account(account)
                .title(raidDto.getTitle())
                .shortDescription(raidDto.getShortDescription())
                .publishedDateTime(LocalDateTime.now())
                .tags(raidDto.getTag()).build();
        raidRepository.save(raid);
        return "redirect:/raid/list-raid";
    }

    public String searchStudy(String keyword, Model model,
                              @PageableDefault(size = 15, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                              Pageable pageable) {

       // Page<Study> studyPage = studyRepository.findByKeyword(keyword, pageable);

        //model.addAttribute("studyPage", studyPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortProperty",
                pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "search";
    }

    @GetMapping("raid/list-raid")
    public String getlistraid(String tags, Model model,
                              @PageableDefault(size = 9, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                              Pageable pageable) {


        Page<Raid> raids;
        if(tags == null){

             raids = raidRepository.findAll(pageable);
        }else{
             raids = raidRepository.findBytags(tags, pageable);
        }

        model.addAttribute("raids", raids);//studyPage
        model.addAttribute("keyword", tags);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "/raid/list-raid";
    }
}
