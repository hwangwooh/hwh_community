package com.example.hwh_community.post;

import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.domain.Post;
import com.example.hwh_community.domain.QAccount;
import com.example.hwh_community.domain.QComment;
import com.example.hwh_community.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.hwh_community.domain.QAccount.account;
import static com.example.hwh_community.domain.QComment.comment1;
import static com.example.hwh_community.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        List<Post> fetch = jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
        return fetch;
    }



    @Override
    public List<Post> findnotice() {
        List<Post> post =  jpaQueryFactory.selectFrom(QPost.post)
                .where(QPost.post.notice.eq(true)).
                orderBy(QPost.post.id.desc())
                .fetch();
        return post;
    }

    @Override
    public List<Post> findbyidapi(Long id) {
        List<Post> fetch = jpaQueryFactory.selectFrom(post)
                .join(post.account,account).fetchJoin()
                .join(post.commentList,comment1).fetchJoin()
                .where(post.id.eq(id))
                .distinct()
                .fetch();
        return fetch;
    }
}
