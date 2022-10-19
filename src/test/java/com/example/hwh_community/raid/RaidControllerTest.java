package com.example.hwh_community.raid;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.signup.WriteUpForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class RaidControllerTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    RaidService raidService;

    @Autowired
    RaidRepository raidRepository;

    @Autowired
    MockMvc mockMvc;


    @DisplayName("레이드 개설 get")
    @Test
    void get_newraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/new-raid")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("raid/new-raid"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("raidDto"));

    }


    @Test
    @DisplayName("레이드 post")
    void post_newraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

     mockMvc.perform(post("/post/write")
                        .param("title", "title2")
                        .param("shortDescription", "shortDescription2")
                        .param("tag", "qwe")
                        .param("maximum", "maximum2")
                        .with(csrf()))
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("레이드 홈")
    void getraidhom() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(3L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        mockMvc.perform(get("/raid/raid-hom/" + newraid.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("raid/raid-hom"))
                .andExpect(model().attributeExists("raid"));

    }

    @Test
    @DisplayName("레이드 post")
    void postraidhom() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        mockMvc.perform(post("/post/write")
                        .param("title", "title2")
                        .param("shortDescription", "shortDescription2")
                        .param("tag", "qwe")
                        .param("maximum", "maximum2")
                        .with(csrf()))
                .andExpect(status().isOk());

    }





}