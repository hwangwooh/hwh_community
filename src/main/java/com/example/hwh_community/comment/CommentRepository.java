package com.example.hwh_community.comment;

import com.example.hwh_community.domain.Comment;
import com.example.hwh_community.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select c from Comment c where c.post.id = :id")
    List<Comment> findCommentsBoardId(@Param("id") Long id);

    List<Comment> findAllByPost(Post post);

}
