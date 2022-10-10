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
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class RaidController {

    private final ModelMapper modelMapper;
    private final RaidRepository raidRepository;

    private final AccountRepository accountRepository;




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
                .tag(raidDto.getTag()).build();
        raidRepository.save(raid);
        return "redirect:/raid/list-raid";
    }

    @GetMapping("raid/list-raid")
    public String getlistraid(String tags, Model model,
                              @PageableDefault(size = 9, sort = "publishedDateTime", direction = Sort.Direction.DESC)
                              Pageable pageable) {
        Page<Raid> raids;
        if(tags == null){
            raids = raidRepository.findAll(pageable);
        }else{
            raids = raidRepository.findBytag(tags, pageable);
        }

        model.addAttribute("raids", raids);
        model.addAttribute("keyword", tags);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
        return "/raid/list-raid";
    }

    @GetMapping("raid/raid-hom/{id}")
    public String getraidhom(@PathVariable("id") Long id, Model model) {
        Raid raid = raidRepository.findById(id).get();
        model.addAttribute("raid",raid);
        return "/raid/raid-hom";
    }

    @PostMapping("raid/list-raid/{id}")
    public String postraidhom(@PathVariable("id") Long id, @Valid RaidDto raidDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Raid raid = raidRepository.findById(id).get();
        Account account = accountRepository.findByNickname(username);

        return "redirect:/raid/raid-hom";
    }


}
