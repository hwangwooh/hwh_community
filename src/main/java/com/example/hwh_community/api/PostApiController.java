package com.example.hwh_community.api;

import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.comment.CommentService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.post.PostDto;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.post.PostSearch;
import com.example.hwh_community.post.PostService;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostRepository postRepository;
    private final PostService postService;

    private CommentRepository commentRepository;

    private CommentService commentService;

    @PostMapping("post/api/write")
    public void writeSubmit(@CurrentAccount Account account, @Valid WriteUpForm writeUpForm, Errors errors) {
        String nickname = account.getNickname();
        postService.write(writeUpForm, nickname);
    }


    @GetMapping("post/api/getList")
    public List<PostDto> getListUpForm(@CurrentAccount Account account, @ModelAttribute PostSearch postSearch) {
        List<PostDto> list2 = postService.getList2(postSearch);
        return list2;
    }

    @GetMapping("post/api/getList2") //공지 사항
    private List<PostDto> getListUpForm2(@CurrentAccount Account account, @ModelAttribute PostSearch postSearch) {

        List<PostDto> postDtos = postService.getnotice2();
        return postDtos;
    }
    @GetMapping("post/api/postContent/{id}")
    public List<PostApiDto> postContent(@PathVariable("id") Long id, Model model) {

        List<PostApiDto> postApiDtos =  postService.getListapi(id);

        return postApiDtos;
    }

}
