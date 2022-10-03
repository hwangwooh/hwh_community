package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<Post> getList(String searchText, Pageable pageable)
    {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        return posts;
    }



    public void edit(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepository.save(post);


    }


    public void delete(Long id) {


        Post post = postRepository.findById(id).orElseThrow();
        postRepository.deleteById(post.getId());
        //jpaQueryFactory.delete(comment).where(comment.post.id.eq(id)).execute();// c


    }
}
