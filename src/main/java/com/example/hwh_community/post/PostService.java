package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public final AccountRepository accountRepository;

    public void write(WriteUpForm postUpForm,String username) {
        Account byNickname = accountRepository.findByNickname(username);
        Post post = Post.builder().title(postUpForm
                        .getTitle())
                .content(postUpForm.getContent())
                .dateTime(LocalDate.now())
                .account(byNickname)
                .build();

        postRepository.save(post);
    }


    public List<PostDto> getList(PostSearch postSearch) {

        List<PostDto> postDtos = postRepository.getList(postSearch).stream().map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .dateTime(post.getDateTime())
                        .build())
                .collect(Collectors.toList());
        return postDtos;

    }
}
