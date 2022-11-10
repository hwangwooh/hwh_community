package com.example.hwh_community.comment;

import com.example.hwh_community.api.Dto.CommentEditor;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public Post commentsvae(CommentDto dto, Post post, Account account) {

        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .dateTime(LocalDate.now())
                .post(post)
                .account(account)
                .build();

        Comment save = commentRepository.save(comment);
        post.addcomment(save);
        return post;
    }

    public Post commentsvae2(CommentEditor commentEditor,Long postid, Account account) {

        Post post = postRepository.findById(postid).get();
        Comment comment = Comment.builder()
                .comment(commentEditor.getComment())
                .dateTime(LocalDate.now())
                .post(post)
                .account(account)
                .build();
        commentRepository.save(comment);

        return post;

    }
}
