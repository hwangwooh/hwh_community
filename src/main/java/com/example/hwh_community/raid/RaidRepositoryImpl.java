package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.hwh_community.domain.QRaid.raid;

@RequiredArgsConstructor
public class RaidRepositoryImpl implements RaidRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Raid> getList(RaidSearch raidSearch) {

        List<Raid> fetch = jpaQueryFactory.selectFrom(raid)
                .limit(raidSearch.getSize())
                .offset(raidSearch.getOffset())
                .orderBy(raid.id.desc())
                .fetch();

        return fetch;
    }

    @Override
    public List<Raid> getListtag(RaidSearch raidSearch, String tag) {

        List<Raid> fetch = jpaQueryFactory.selectFrom(raid)
                .limit(raidSearch.getSize())
                .offset(raidSearch.getOffset())
                .where(raid.tag.eq(tag))
                .orderBy(raid.id.desc())
                .fetch();

        return fetch;

    }

    @Override
    public List<Raid> getListme(RaidSearch raidSearch, Account account) {

        List<Raid> fetch = jpaQueryFactory.selectFrom(raid)
                .limit(raidSearch.getSize())
                .offset(raidSearch.getOffset())
                .where(raid.members.contains(account))
                .orderBy(raid.id.desc())
                .fetch();

        return fetch;
    }
}
