package com.example.hwh_community.api;

import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.api.Dto.CommentEditor;
import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.comment.CommentDto;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.comment.CommentService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.post.PostDto;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.post.PostSearch;
import com.example.hwh_community.post.PostService;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
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
    public void writeSubmit(@CurrentAccount Account account,@RequestBody @Valid WriteUpForm writeUpForm) {
        String nickname = account.getNickname();
        postService.write(writeUpForm, nickname);
    }


    @GetMapping("post/api/getList")
    public List<PostApiDto> getListUpForm(@CurrentAccount Account account, @ModelAttribute PostSearch postSearch) {
        List<PostApiDto> list2 = postService.getList2(postSearch);
        return list2;
    }

    @GetMapping("post/api/getList2") //공지 사항
    private List<PostApiDto> getListUpForm2(@CurrentAccount Account account, @ModelAttribute PostSearch postSearch) {

        List<PostApiDto> postDtos = postService.getnotice2();
        return postDtos;
    }
    @GetMapping("post/api/postContent/{id}")
    public PostApiDto postContent(@PathVariable("id") Long id) {

        PostApiDto postApiDtos = postService.getpost(id);
        if (postApiDtos.getContent() == ""){
            return postApiDtos;}
        return postApiDtos;
    }

    @PostMapping("post/api/comment/{id}") // 코멘트 쓰기
    public PostApiDto addComment(@PathVariable("id") Long id,@RequestBody @Valid CommentEditor commentEditor,@CurrentAccount Account account) {

        Post post = postRepository.findById(id).get();
        commentService.commentsvae2(commentEditor, post, account);

        return new PostApiDto(post);
    }

    @GetMapping("post/api/edit/{id}")
    public PostDto getedit(@PathVariable("id") Long id, Model model) {

        Post post = postRepository.findById(id).get();
       PostDto postDto = new PostDto(post.getId(),post.getTitle(),post.getContent().replace("<br>","\r\n"),post.getDateTime(),post.getAccount().getNickname(),post.getCountVisit());

        return postDto;


    }

    @PatchMapping("post/api/edit/{id}")
    public void postedit(@CurrentAccount Account account,@PathVariable("id") Long id,@RequestBody @Valid PostDto postDto) {

        postService.edit(id, postDto, account);

    }

    /**
     *
     * 수정예정 다른 사림도 지울수 잇음
     */
    @DeleteMapping("post/postdelete/{id}")
    public void postdelete(@CurrentAccount Account account,@PathVariable("id") Long id) {

        postService.delete(account,id);
    }


}
