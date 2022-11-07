package com.example.hwh_community.raid;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.api.Dto.AccountApiDto;
import com.example.hwh_community.api.Dto.RaidApiDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.signup.WriteUpForm;
import com.querydsl.jpa.JPQLQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired EntityManager em;
    @DisplayName("레이드 개설 get")
    @Test
    void get_newraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/new-raid")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/new-raid"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("raidDto"));

    }


    @Test
    @DisplayName("레이드 개설 post")
    void post_newraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

     mockMvc.perform(post("/raid/new-raid")
                        .param("title", "title2")
                        .param("shortDescription", "shortDescription2")
                        .param("tag", "qwe")
                        .param("maximum", "maximum2")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("레이드 lsit get")
    @Test
    void getlistraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid?tag=발탄")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid"))
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

    }
    @DisplayName("레이드 lsit2 get 테그가 널일때")
    @Test
    void getlistraid2() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid?tag=null")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid2"))
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

    }
    @DisplayName("레이드 lsit me get 내가 참여 중인 레이드만")
    @Test
    void getlistraidme() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid/me?tag=null")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid"))
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

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
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/raid-hom"))
                .andExpect(model().attributeExists("raid"));

    }

    @Test
    @DisplayName("레이드 참가")
    void postraidhom() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(3L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        Account account = accountRepository.findByNickname("zaq8077");
        accountService.login(account);

        mockMvc.perform(get("/raid/raid-hom/"+newraid.getId()+"/add/"+account.getNickname())
                        .with(csrf())).andDo(print())
                .andExpect(view().name("redirect:/raid/raid-hom/"+newraid.getId()))
                .andExpect(status().is3xxRedirection());


    }

    @Test
    @DisplayName("레이드 참가 인원 초과시")
    void postraidhom_not() throws Exception
    {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        Account account = accountRepository.findByNickname("zaq8077");
        accountService.login(account);

        mockMvc.perform(get("/raid/raid-hom/"+newraid.getId()+"/add/"+account.getNickname())
                        .with(csrf()))
                .andExpect(view().name("redirect:/raid/raid-hom/"+newraid.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"));


    }

    @Test
    @DisplayName("멤버탈퇴")
    void 멤버탈퇴() throws Exception
    {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        Account account = accountRepository.findByNickname("zaq8077");
        accountService.login(account);
        raidService.addmember(newraid, account.getNickname());

        mockMvc.perform(get("/raid/raid-hom/" + newraid.getId() + "/remove/" + account.getNickname())
                        .with(csrf()))
                .andExpect(view().name("redirect:/raid/raid-hom/" + newraid.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

    }






}