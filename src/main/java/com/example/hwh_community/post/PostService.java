package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public void write(PostUpForm postUpForm) {

        Post post = Post.builder().title(postUpForm
                        .getTitle())
                .content(postUpForm.getContent())
                .build();

        postRepository.save(post);
    }

}
