package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.api.Dto.RaidEditer;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.raid.RaidRepository;
import com.example.hwh_community.raid.RaidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class raidApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RaidService raidService;
    @Autowired
    private RaidRepository raidRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    @DisplayName("레이드만들기")
    public void 레이드만들기() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        String Json = objectMapper.writeValueAsString(raidEditer);



        mockMvc.perform(MockMvcRequestBuilders.post("/raid/api/new-raid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        Raid raid = raidRepository.findAll().get(raidRepository.findAll().size()-1);

        assertEquals(raid.getTitle(), "제곰ㄱ");

    }

    @Test
    @DisplayName("레이드리스트 조회")
    public void 레이드조회() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);

        mockMvc.perform(MockMvcRequestBuilders.get("/raid/api/list-raid1/{tag}?page=0&size=10",raid.getTag())
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("레이드 상세")
    public void 레이드상세() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);

        mockMvc.perform(MockMvcRequestBuilders.get("/raid/api/raid-hom/{id}",raid.getId())
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("레이드참가")
    public void 레이드참가() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);

        Account test1 = accountRepository.findByNickname("test1");
        accountService.login(test1);




        mockMvc.perform(MockMvcRequestBuilders.post("/raid/api/raid-hom/{id}/add/{member}",raid.getId(),test1.getNickname())
                        .contentType(MediaType.APPLICATION_JSON)

                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(raid.getMembers().size(), 2);

    }

    @Test
    @DisplayName("탈퇴")
    public void 레이드탈퇴() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);

        Account test1 = accountRepository.findByNickname("test1");
        accountService.login(test1);
        raid.inMemeber(test1);

        String Json = objectMapper.writeValueAsString(raidEditer);


        mockMvc.perform(MockMvcRequestBuilders.post("/raid/api/raid-hom/{id}/remove/{member}",raid.getId(),test1.getNickname())
                        .contentType(MediaType.APPLICATION_JSON)

                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(raid.getMembers().size(), 1);

    }


    @Test
    @DisplayName("강퇴")
    public void 레이드추방() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);

        Account test1 = accountRepository.findByNickname("test1");
        accountService.login(test1);
        raid.inMemeber(test1);


        String Json = objectMapper.writeValueAsString(raidEditer);


        mockMvc.perform(MockMvcRequestBuilders.patch("/raid/api/memberssetdelete/{raidid}/{id}",raid.getId(),test1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("레이드수정")
    public void 레이드수정() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);
        RaidEditer raidEditer1 = new RaidEditer();
        raidEditer1.setTag("444");
        raidEditer1.setMaximum(3L);
        raidEditer1.setShortDescription("111");
        raidEditer1.setTitle("444");

        String Json = objectMapper.writeValueAsString(raidEditer1);



        mockMvc.perform(MockMvcRequestBuilders.post("/raid/api/raidset/{id}",raid.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("모집설정")
    public void 모집설정() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        RaidEditer raidEditer = new RaidEditer();
        raidEditer.setTag("t");
        raidEditer.setMaximum(4L);
        raidEditer.setShortDescription("21333333333");
        raidEditer.setTitle("제곰ㄱ");
        Raid raid = raidService.apinewraid(zaq8077, raidEditer);






        mockMvc.perform(MockMvcRequestBuilders.patch("/raid/api/raid-hom/published/{id}",raid.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());


        assertEquals(raid.isPublished(), false);

    }












}