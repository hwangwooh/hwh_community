package com.example.hwh_community.post;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.comment.CommentDto;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.comment.CommentService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final AccountRepository accountRepository;

    private final PostService postService;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("post/write")
    public String writeUpForm(Model model) {
        model.addAttribute("writeUpForm", new WriteUpForm());//"WriteUpForm" 생약 가능
        return "post/write";
    }

    @PostMapping("post/write")
    public String writeSubmit( @Valid WriteUpForm writeUpForm, Errors errors) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        if (errors.hasErrors()) {
            return "post/write";
        }
        postService.write(writeUpForm,username);

        return "redirect:/post/getList";
    }

    @GetMapping("post/getList")
    public String getListUpForm(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        List<Post> notice = postService.getnotice();
        Page<Post> boards = postService.getList(searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);

        model.addAttribute("notice", notice);
        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "post/getList";

    }


    @GetMapping("post/postContent/{id}")
    public String postContent(@PathVariable("id") Long id, Model model) {


        Post post2 = postRepository.findById(id).get();
        List<Comment> comments = commentRepository.findCommentsBoardId(id);
        Post post = postService.Visit(post2);
        model.addAttribute(post);
        model.addAttribute("comments", comments);
        return "post/postContent";
    }

    @PostMapping("post/postContent/{id}")
    public String addComment(@CurrentAccount Account account,@PathVariable("id") Long id, @Valid CommentDto commentDto, Model model) {

        Post post = postRepository.findById(id).get();
        commentService.commentsvae(commentDto, post, account);
        List<Comment> comments = commentRepository.findCommentsBoardId(id);

        model.addAttribute("comments", comments);
        model.addAttribute(post);
        return "redirect:"+id;

    }

    @GetMapping("post/edit/{id}")
    public String getedit(@PathVariable("id") Long id, Model model) {



        PostDto postDto = postService.getedit(id);
        model.addAttribute(postDto);

        return "post/edit";
    }

    @PostMapping("post/edit/{id}")
    public String postedit(@PathVariable("id") Long id,@Valid PostDto postDto,Errors errors) {

        if (errors.hasErrors()) {
            return "redirect:/post/edit/"+id;
        }
        postService.edit(id, postDto);
        return "redirect:/post/postContent/"+id;
    }

    @GetMapping("post/postdelete/{id}")
    public String postdelete(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {

        postService.delete(id);
        return "redirect:/post/getList";

    }





}
