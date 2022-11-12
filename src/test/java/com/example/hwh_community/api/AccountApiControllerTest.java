package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.api.Dto.RaidEditer;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.signup.SignUpForm;
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

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입")
    public void 회원가입() throws Exception {


        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail("ggasd@email.com");
        signUpForm.setPassword("123232122");
        signUpForm.setNickname("ghkddng");
        String Json = objectMapper.writeValueAsString(signUpForm);




        mockMvc.perform(MockMvcRequestBuilders.post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        Account ghkddng = accountRepository.findByNickname("ghkddng");
        assertEquals(ghkddng.getNickname(), "ghkddng");


    }

}