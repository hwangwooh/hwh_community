package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {


    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;
    @Test
    @DisplayName("글쓰기")
    void 글쓰기() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        mockMvc.perform(post("/post/write")
                        .param("title", "title")
                        .param("content", "content")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/post/getList"));

        Post post = postRepository.findAll().get(0);
        assertNotNull(post);
        assertNotEquals(post.getTitle(), "title");
    }

    @DisplayName("글쓰기 - 실패")
    @Test
    void 글쓰기실패() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        mockMvc.perform(post("/post/write")
                        .param("title", "title")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("post/write"));

    }

    @DisplayName("글쓰기 - 조회")
    @Test
    void 글조회() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("con");
        writeUpForm.setTitle("title");
        Post write = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(get("/post/postContent/"+write.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("post/postContent"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("comments"));

    }

    @DisplayName("코멘트쓰기")
    @Test
    void 코멘트쓰기() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("con");
        writeUpForm.setTitle("title");
        Post write = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(post("/post/postContent/" + write.getId())
                        .param("comment", "comment123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:"+write.getId()));


    }
    @DisplayName("글 수정")
    @Test
    void 글수정() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("con");
        writeUpForm.setTitle("title");
        Post write = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(post("/post/edit/" + write.getId())
                        .param("title", "title333")
                        .param("content", "content333")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/post/postContent/"+write.getId()));


    }

    @DisplayName("글수정")
    @Test
    void 글수정페이지() throws Exception {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("con");
        writeUpForm.setTitle("title");
        Post write = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(get("/post/edit/"+write.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("post/edit"))
                .andExpect(model().attributeExists("postDto"));

    }
    @DisplayName("글삭제")
    @Test
    void 글삭제() throws Exception
    {
        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("con");
        writeUpForm.setTitle("title");
        Post write = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(get("/post/postdelete/" + write.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/post/getList"));

    }






}