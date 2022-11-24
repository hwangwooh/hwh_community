package com.example.hwh_community.account;

import com.example.hwh_community.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public  List<Account> Accountalldata(Account account) {

        List<Account> fetch = jpaQueryFactory.selectFrom(QAccount.account)
                .join(QAccount.account.postList, QPost.post).fetchJoin().where(QPost.post.account.id.eq(account.getId()))
                .join(QAccount.account.raid_account, QRaid.raid).fetchJoin().where(QRaid.raid.account.id.eq(account.getId()))
                .join(QAccount.account.comments, QComment.comment1).fetchJoin().where(QComment.comment1.account.id.eq(account.getId()))
                .join(QAccount.account.raid_memders, QRaid.raid).fetchJoin().where(QRaid.raid.account.id.eq(account.getId()))
                .distinct().fetch();

        return fetch;
    }
}
