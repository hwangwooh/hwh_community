package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Test
    @DisplayName("글 작성")
    public void test1() throws Exception {
        // given

        PostUpForm postUpForm = new PostUpForm();
        postUpForm.setTitle("test-t");
        postUpForm.setContent("test-c");

        // when
        postService.write(postUpForm);
        // then
        Assertions.assertEquals(1L,postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("test-t", post.getTitle());
        Assertions.assertEquals("test-c", post.getContent());

    }


}