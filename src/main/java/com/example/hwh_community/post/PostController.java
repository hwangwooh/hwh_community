package com.example.hwh_community.post;

import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.comment.CommentService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("/write")
    public String writeUpForm(Model model) {
        model.addAttribute("writeUpForm", new WriteUpForm());//"WriteUpForm" 생약 가능
        return "post/write";
    }

    @PostMapping("/write")
    public String writeSubmit(@Valid WriteUpForm writeUpForm, Errors errors) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        if (errors.hasErrors()) {
            return "post/write";
        }
        postService.write(writeUpForm,username);

        return "redirect:/";
    }

//    public String getListUpForm(@ModelAttribute PostSearch postSearch,Model model) {
//        List<PostDto> postDtos = postService.getList(postSearch);
//        model.addAttribute("postDtos",postDtos);//"WriteUpForm" 생약 가능
//        return "post/getList";
//    }
//



    @GetMapping("/getList")
    public String getListUpForm(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Post> boards = postRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);


        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);

        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "post/getList";
    }

    @GetMapping("/postContent/{id}")
    public String postContent(@PathVariable("id") Long id, Model model) {


        Post post = postRepository.findById(id).get();


        List<Comment> comments = commentRepository.findCommentsBoardId(id);

        model.addAttribute(post);
        model.addAttribute("comments", comments);

        return "/post/postContent";
    }


}
