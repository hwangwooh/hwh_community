package com.example.hwh_community.login;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class loginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @DisplayName("회원 가입 get")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"));
    }

    @DisplayName("회원 가입 post")
    @Test
    void 회원가입() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "akskfjh22")
                        .param("email", "ghk@email.com")
                        .param("password", "1233333333")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Account account = accountRepository.findByNickname("akskfjh22");
        assertNotEquals(account.getEmail(), "ghk@email.com");
    }

    @DisplayName("회원 가입 실패")
    @Test
    void 회원가입실패() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "akskfjh22")
                        .param("email", "ghk@email.com")
                        .param("password", "1233333332344444444444444444444444444444444444444444443")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
        Account account = accountRepository.findByNickname("akskfjh22");
        assertNull(account);


    }



}
