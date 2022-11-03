package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;

import java.util.List;

public interface RaidRepositoryCustom {

    List<Raid> getList(RaidSearch raidSearch);

    List<Raid> getListtag(RaidSearch raidSearch,String tag);

    List<Raid> getListme(RaidSearch raidSearch, Account account);
}
