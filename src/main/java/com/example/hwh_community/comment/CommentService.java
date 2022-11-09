package com.example.hwh_community.comment;

import com.example.hwh_community.api.Dto.CommentEditor;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public void commentsvae(CommentDto dto, Post post, Account account) {

        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .dateTime(LocalDate.now())
                .post(post)
                .account(account).build();

        commentRepository.save(comment);

    }

    public void commentsvae2(CommentEditor commentEditor, Post post, Account account) {

        Comment comment = Comment.builder()
                .comment(commentEditor.getComment())
                .dateTime(LocalDate.now())
                .post(post)
                .account(account).build();

        commentRepository.save(comment);

    }
}
