package com.example.hwh_community.admin;

import com.example.hwh_community.account.AccountRepository;
import com.example.hwh_community.comment.CommentRepository;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.Raid;
import com.example.hwh_community.post.PostRepository;
import com.example.hwh_community.post.PostService;
import com.example.hwh_community.raid.RaidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServic {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final RaidRepository raidRepository;

    @Transactional
    public void all(){

        List<Account> all = accountRepository.findAll();
        for (Account account : all) {
            List<Raid> all1 = raidRepository.findAllByAccount(account);
            for (Raid raid : all1) {
                account.addraid_account(raid);
            }
            List<Post> all2 = postRepository.findAllByAccount(account);
            for (Post post : all2) {
                account.addpostList(post);
            }
            List<Comment> all3= commentRepository.findAllByAccount(account);
            for (Comment comment : all3) {
                account.addcomments(comment);
            }
            List<Raid> all4 = raidRepository.findAllByMembers(account);
            for (Raid raid : all4) {
                account.addraid_memders(raid);
            }

        }
    }


}
