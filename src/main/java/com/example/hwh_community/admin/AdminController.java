package com.example.hwh_community.admin;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.ROLE;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.post.PostService;
import com.example.hwh_community.raid.Gametype;
import com.example.hwh_community.raid.RaidDto;
import com.example.hwh_community.raid.RaidRepository;
import com.example.hwh_community.signup.SignUpForm;
import com.example.hwh_community.signup.WriteUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final RaidRepository raidRepository;
    private final AdminServic adminServic;
    private final AccountService accountService;

    @GetMapping("admin/admin_hom")
    public String admin_hom(@CurrentAccount Account admin, Model model,
                            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if(admin.getRole() == ROLE.ROLE_USER){
            return "index";
        }
        Page<Account> accounts = accountRepository.findAll(pageable);
        model.addAttribute("accounts",accounts);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("id") ? "id" : "id");
        return "admin/adminhom";
    }

    @GetMapping("admin/admin_hom/{id}")
    public String Account_del(@CurrentAccount Account admin, @PathVariable("id") Long id, Model model){
        Account account = accountRepository.findById(id).get();

        if(account.getRole() == ROLE.ROLE_ADMIN){
            return "redirect:/admin/admin_hom";
        }
        
        accountRepository.deleteById(id);
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts",accounts);
        return "redirect:/admin/admin_hom";
    }

    @GetMapping("admin/account/{id}")
    public String Account_get(@CurrentAccount Account admin, @PathVariable("id") Long id, Model model){

        if(admin.getRole() == ROLE.ROLE_ADMIN){
            return "redirect:/admin/admin_hom";
        }

        Account accountalldata = accountService.getAccountalldata(id).get(0);

        model.addAttribute("accountalldata",accountalldata);


        return "redirect:/admin/admin_hom";
    }


    @GetMapping("admin/admin_post")
    public String admin_post(@CurrentAccount Account admin, Model model,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
                             ) {

        if(admin.getRole() == ROLE.ROLE_USER){
            return "index";
        }

        Page<Post> posts = postRepository.findAll(pageable);
        model.addAttribute("posts",posts);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("id") ? "id" : "id");
        return "admin/adminpost";
    }

    @GetMapping("admin/admin_post/{id}")
    public String post_del(@CurrentAccount Account admin, @PathVariable("id") Long id, Model model){

        Post post = postRepository.findById(id).get();
        if(post.isNotice()){
            postRepository.deleteById(id);

            return "redirect:/admin/write";
        }

        postRepository.deleteById(id);

        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);

        return "redirect:/admin/admin_post";
    }
    @GetMapping("admin/admin_raid")
    public String admin_raid(@CurrentAccount Account admin, Model model,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if(admin.getRole() == ROLE.ROLE_USER){
            return "index";
        }
        Page<Raid> raids = raidRepository.findAll(pageable);
        model.addAttribute("raids",raids);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("id") ? "id" : "id");
        return "admin/adminraid";
    }

    @GetMapping("admin/admin_raid/{id}")
    public String raid_del(@CurrentAccount Account admin, @PathVariable("id") Long id, Model model){


        raidRepository.deleteById(id);

        List<Raid> raids = raidRepository.findAll();
        model.addAttribute("raids",raids);

        return "redirect:/admin/admin_raid";
    }

    @GetMapping("admin/write")
    public String noticewriteUpForm(Model model) {

        List<Post> notice = postService.getnotice();
        model.addAttribute("writeUpForm", new WriteUpForm());//"WriteUpForm" 생약 가능
        model.addAttribute("notice", notice);//"WriteUpForm" 생약 가능
        return "admin/write";
    }

    @PostMapping("admin/write")
    public String noticewriteSubmit(@CurrentAccount Account admin, @Valid WriteUpForm writeUpForm, Errors errors) {
        if(admin.getRole() == ROLE.ROLE_USER){
            return "index";
        }

        if (errors.hasErrors()) {
            return "admin/write";
        }
        postService.write2(writeUpForm,admin);

        return "redirect:/admin/write";
    }






}
