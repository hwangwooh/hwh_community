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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@Transactional
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
    @DisplayName("????????? ?????? get")
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
    @DisplayName("????????? ?????? post")
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

    @DisplayName("????????? lsit get")
    @Test
    void getlistraid() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid-tag?tag=??????")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid"))
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

    }
    @DisplayName("????????? lsit2 get ????????? ?????????")
    @Test
    void getlistraid2() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid2"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

    }
    @DisplayName("????????? lsit me get ?????? ?????? ?????? ????????????")
    @Test
    void getlistraidme() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);


        mockMvc.perform(get("/raid/list-raid/me")
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/list-raid-me"))
                .andExpect(model().attributeExists("raids"))
                .andExpect(model().attributeExists("sortProperty"));

    }

    @Test
    @DisplayName("????????? ???")
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
    @DisplayName("????????? ??????")
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

        assertTrue(newraid.getMembers().contains(account));
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????????")
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

        assertFalse(newraid.getMembers().contains(account));
    }

    @Test
    @DisplayName("????????????")
    void ????????????() throws Exception
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

        assertFalse(newraid.getMembers().contains(account));

    }

    @Test
    @DisplayName("???????????????")
    void ???????????????() throws Exception
    {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);



        mockMvc.perform(get("/raid/raid-hom/delete/" + newraid.getId())
                        .with(csrf()))
                .andExpect(view().name("redirect:/raid/list-raid"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());


    }

    @Test
    @DisplayName("??????????????? ?????? ???????????? ?????? ?????????")
    void ?????????????????????() throws Exception
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


        mockMvc.perform(get("/raid/raid-hom/delete/" + newraid.getId())
                        .with(csrf()))
                .andExpect(view().name("index"))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("????????? ??????")
    void ???????????????() throws Exception
    {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);


        mockMvc.perform(get("/raid/membersset/"+newraid.getId())
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/raid-members"))
                .andExpect(model().attributeExists("raid"));


    }

    @DisplayName("????????? ?????? ??????")
    @Test
    void ??????() throws Exception {
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

        accountService.login(zaq8077);

        mockMvc.perform(get("/raid/memberssetdelete/"+newraid.getId()+"/"+account.getId())
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/raid-members"))
                .andExpect(model().attributeExists("raid"));

        assertFalse(newraid.getMembers().contains(account));

    }


    @DisplayName("????????? ?????? get")
    @Test
    void ???????????????() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        mockMvc.perform(get("/raid/raidset/"+newraid.getId())
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/raid-setting"))
                .andExpect(model().attributeExists("raid"))
                .andExpect(model().attributeExists("raidDto"));

    }

    @Test
    @DisplayName("????????? ?????? psot")
    void ???????????????post() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        mockMvc.perform(post("/raid/raidset/"+newraid.getId())
                        .param("title", "title2333")
                        .param("shortDescription", "shortDescription2333")
                        .param("tag", "qwe")
                        .param("maximum", "5")
                        .with(csrf())).andDo(print())
                .andExpect(status().is3xxRedirection());

        assertEquals(newraid.getTitle(), "title2333");
        assertEquals(newraid.getShortDescription(), "shortDescription2333");

    }

    @DisplayName("????????? ??????")
    @Test
    void ?????????() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("test1");
        accountService.login(zaq8077);
        Account zaq80771 = accountRepository.findByNickname("zaq8077");
        RaidDto raidDto = new RaidDto();
        raidDto.setTitle("124124");
        raidDto.setShortDescription("124124");
        raidDto.setTag("qqq");
        raidDto.setMaximum(1L);
        Raid newraid = raidService.newraid(zaq8077, raidDto);

        mockMvc.perform(get("/raid/profile/"+zaq80771.getNickname())
                        .with(csrf())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("raid/profile"))
                .andExpect(model().attributeExists("account"));

    }






}