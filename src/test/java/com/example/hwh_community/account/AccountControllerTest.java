package com.example.hwh_community.account;

import com.example.hwh_community.admin.AdminServic;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.raid.RaidRepository;
import com.example.hwh_community.signup.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @Autowired
    RaidRepository raidRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AdminServic adminServic;
    @BeforeEach
    public void beforeEach() {
        // 회원 가입
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("hwh123");
        signUpForm.setEmail("hwh123@email.com");
        signUpForm.setPassword("123123123");
        accountService.saveNewAccount(signUpForm);
    }

    @AfterEach
    public void afterEach() {
        // 저장된 회원 모두 삭제
        accountRepository.deleteAll();
    }


    @DisplayName("회원 가입- 정상")
    @Test
    void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "test12322")
                        .param("email", "test1232@email.com")
                        .param("password", "123123123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated().withUsername("test12322"));

        Account account = accountRepository.findByEmail("test1232@email.com");
        assertNotNull(account);
        assertNotEquals(account.getPassword(), "123123123");// 암호화 확인
    }

    @DisplayName("회원 가입 - 비정상 ")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "test12322")
                        .param("email", "test1232@email.com")
                        .param("password", "1234111111111111111112341111111111111111" +
                                "111111111111111111111111111151234111111111111111111111111111111111" +
                                "11111111111512341111111111111111111111111111111111111111" +
                                "1111512341111111111111111111111111111111111111111111151" +
                                "1111111111111111111111111115") // 50글자 이상
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(unauthenticated());
    }
    @DisplayName("로그인 성공")
    @Test
    public void login_with_nickname() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "hwh123")
                        .param("password", "123123123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("hwh123"));
    }

    @DisplayName("로그인 실패")
    @Test
    public void login_fail() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "hwh123")
                        .param("password", "1233123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }
    @DisplayName("로그아웃")
    @Test
    public void logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }

    @DisplayName("tewst")
    @Test
    public void all() throws Exception {
//        Account zaq8077 = accountRepository.findByNickname("zaq8077");
//        List<Raid> allByAccount = raidRepository.findAllByAccount(zaq8077);
//        for (Raid raid : allByAccount) {
//            zaq8077.addraid_account(raid);
//        }
//


        //System.out.printf(accountalldata.toString());
        Account zaq8077 = accountRepository.findByNickname("test1");
        Account accountalldata = accountService.getAccountalldata(zaq8077.getId()).get(0);
        accountalldata.getNickname();



    }

}