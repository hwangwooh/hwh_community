package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    public Post write(WriteUpForm postUpForm,String username) {
        Account byNickname = accountRepository.findByNickname(username);
        Post post = Post.builder().title(postUpForm
                        .getTitle())
                .content(postUpForm.getContent().replace("\r\n","<br>")) //
                .dateTime(LocalDate.now())
                .countVisit(0L)
                .account(byNickname)
                .build();

        Post save = postRepository.save(post);
        return save;
    }

    public Post write2(WriteUpForm postUpForm,Account username) {

        Post post = Post.builder().title(postUpForm
                        .getTitle())
                .content(postUpForm.getContent().replace("\r\n","<br>")) //
                .dateTime(LocalDate.now())
                .notice(true)
                .countVisit(0L)
                .account(username)
                .build();

        Post save = postRepository.save(post);
        return save;
    }


    public Page<Post> getList(String searchText, Pageable pageable)
    {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);

        return posts;
    }

    public List<Post> getnotice() {
        List<Post> post = postRepository.findnotice();
        return post;
    }


    public List<PostDto> getnotice2() {
        List<Post> post = postRepository.findnotice();
        List<PostDto> postDtos = new ArrayList<>();
        modelMapper.map(post,postDtos);
        return postDtos;
    }


    public List<PostDto> getList2(PostSearch postSearch) {

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
        postRepository.delete(post);
        //jpaQueryFactory.delete(comment).where(comment.post.id.eq(id)).execute();// c


    }

    public Post Visit(@NotNull Post post) {
        post.Visitcount();
        Post save = postRepository.save(post);
        return save;
    }


    public List<PostApiDto> getListapi(Long id) {

        List<Post> findbyidapi = postRepository.findbyidapi(id);
        List<PostApiDto> postApiDto = new ArrayList<>();
        modelMapper.map(findbyidapi, postApiDto);
        return postApiDto;
    }


}
