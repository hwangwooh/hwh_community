package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.api.Dto.CommentApiDto;
import com.example.hwh_community.api.Dto.CommentEditor;
import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.comment.CommentDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.post.PostService;
import com.example.hwh_community.signup.WriteUpForm;
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

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    PostService postService;

    @Test
    @DisplayName("게시물 쓰기")
    public void test2() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        String Json = objectMapper.writeValueAsString(writeUpForm);



        mockMvc.perform(MockMvcRequestBuilders.post("/post/api/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
        Post post = postRepository.findAll().get(1);
        assertNotEquals(post.getTitle(), "내용입니당111");
    }

    @Test
    @DisplayName("공지조회")
    public void 공지조회() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);



        mockMvc.perform(MockMvcRequestBuilders.get("/post/api/getList?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("조회")
    public void 조회() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);



        mockMvc.perform(MockMvcRequestBuilders.get("/post/api/getList2?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("글조회")
    public void 글조회() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        Post post = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(MockMvcRequestBuilders.get("/post/api/postContent/{id}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("뎃글작성")
    public void 코멘트() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        Post post = postService.write(writeUpForm, zaq8077.getNickname());

        CommentDto dto = new CommentDto();
        dto.setComment("asdsssssssss");
        CommentEditor commentEditor = new CommentEditor();
        commentEditor.setComment("asdasdasd");

        mockMvc.perform(post("/post/api/comment/{id}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertNotEquals(post.getCommentList().size(), "1");


    }
    @Test
    @DisplayName("수정조회")
    public void 수정조회() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        Post post = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(MockMvcRequestBuilders.get("/post/api/edit/{id}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }


    @Test
    @DisplayName("게시물 수정")
    public void 게시물수정() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        Post post = postService.write(writeUpForm, zaq8077.getNickname());

        WriteUpForm writeUpForm2 = new WriteUpForm();
        writeUpForm2.setContent("내용입니당2");
        writeUpForm2.setTitle("내용입니당2");
        String Json = objectMapper.writeValueAsString(writeUpForm2);



        mockMvc.perform(MockMvcRequestBuilders.patch("/post/api/edit/{id}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());



    }

    @Test
    @DisplayName("게시물삭제")
    public void 게시물삭제() throws Exception {

        Account zaq8077 = accountRepository.findByNickname("zaq8077");
        accountService.login(zaq8077);

        WriteUpForm writeUpForm = new WriteUpForm();
        writeUpForm.setContent("내용입니당111");
        writeUpForm.setTitle("내용입니당11");
        Post post = postService.write(writeUpForm, zaq8077.getNickname());


        mockMvc.perform(MockMvcRequestBuilders.delete("/post/postdelete/{id}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

    }






}