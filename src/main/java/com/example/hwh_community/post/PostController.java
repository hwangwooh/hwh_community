package com.example.hwh_community.post;

import com.example.hwh_community.signup.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;


    @GetMapping("/write-view")
    public String writeUpForm(Model model) {
        model.addAttribute("writeUpForm", new PostUpForm());//"signUpForm" 생약 가능
        return "post/write-view";
    }

    @PostMapping("/write-view")
    public String writeSubmit(@Valid PostUpForm postUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "post/write-view";
        }
        postService.write(postUpForm);

        return "redirect:/";
    }
}
