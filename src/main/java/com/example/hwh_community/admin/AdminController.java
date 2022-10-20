package com.example.hwh_community.admin;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.raid.RaidDto;
import com.example.hwh_community.raid.RaidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RaidRepository raidRepository;


    @GetMapping("admin/admin_hom")
    public String admin_hom(@CurrentAccount Account admin, Model model,
                            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts",accounts);
        return "/admin/adminhom";
    }

    @GetMapping("admin/admin_post")
    public String admin_post(@CurrentAccount Account admin, Model model,
                             @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
                             ) {

        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "/admin/adminpost";
    }
    @GetMapping("admin/admin_raid")
    public String admin_raid(@CurrentAccount Account admin, Model model,
                             @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        List<Raid> raids = raidRepository.findAll();
        model.addAttribute("raids",raids);
        return "/admin/adminraid";
    }

}
