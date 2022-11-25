package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidRepository extends JpaRepository<Raid,Long> ,RaidRepositoryCustom{

    Page<Raid> findBytag(String tag, Pageable pageable);

    Page<Raid> findBymembers(Account account, Pageable pageable);
    Page<Raid> findAllBymembers(Account account, Pageable pageable);
    Page<Raid> findAllBymembersAndGametype(Account account,Gametype gametype,Pageable pageable);
    Page<Raid> findAllByGametypeAndTag(Gametype gametype, String tag, Pageable pageable);


    Page<Raid> findAllByGametype(Gametype gametype, Pageable pageable);

     List<Raid> findAllByAccount(Account zaq8077);

    List<Raid> findAllByMembers(Account account);
}
