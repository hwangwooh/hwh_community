package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {


    @Autowired
    PostRepository postRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("글 여러개 조회")
    public void test5() throws Exception {
        // given
        List<Post> postList = IntStream.range(1, 31).mapToObj(i -> Post.builder()
                .title("글제목당"+ i)
                .content("내용입니당" + i)
                .build()).collect(Collectors.toList());
        postRepository.saveAll(postList);
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC);
        mockMvc.perform(MockMvcRequestBuilders.get("/getList"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}