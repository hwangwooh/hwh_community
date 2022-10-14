package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
                .content(postUpForm.getContent().replace("\r\n","<br>")) //
                .dateTime(LocalDate.now())
                .countVisit(0L)
                .account(byNickname)
                .build();

        postRepository.save(post);
    }


    public Page<Post> getList(String searchText, Pageable pageable)
    {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        return posts;
    }


    public List<PostDto> getList2(PostSearch postSearch) {//?page=0&size=10
        //return postRepository.findAll().stream().map(post -> new PostResponse(post)).collect(Collectors.toList());
        // Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));

        return postRepository.getList(postSearch).stream().map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .dateTime(post.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }



    public void edit(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent().replace("\r\n","<br>"));
        postRepository.save(post);


    }


    public void delete(Long id) {


        Post post = postRepository.findById(id).orElseThrow();
        postRepository.deleteById(post.getId());
        //jpaQueryFactory.delete(comment).where(comment.post.id.eq(id)).execute();// c


    }

    public Post Visit(@NotNull Post post) {
        post.Visitcount();
        Post save = postRepository.save(post);
        return save;
    }
}
