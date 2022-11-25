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
                .join(QAccount.account.postList, QPost.post).fetchJoin()
                .join(QAccount.account.raid_account, QRaid.raid)
                .join(QAccount.account.comments, QComment.comment1)
                .join(QAccount.account.raid_memders, QRaid.raid).distinct().where(QAccount.account.id.eq(account.getId()))
                .fetch();

        return fetch;
    }
}
