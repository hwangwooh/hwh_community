package com.example.hwh_community.comment;

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
    public void commentsvae(CommentDto commentDto, Post post, Account account) {

        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .dateTime(LocalDate.now())
                .post(post)
                .account(account).build();

        commentRepository.save(comment);

    }
}
