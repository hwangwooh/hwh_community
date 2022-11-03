package com.example.hwh_community.raid;

import com.example.hwh_community.domain.Account;
import com.example.hwh_community.domain.Raid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidRepository extends JpaRepository<Raid,Long> ,RaidRepositoryCustom{

    Page<Raid> findBytag(String tag, Pageable pageable);

    Page<Raid> findBymembers(Account account, Pageable pageable);
}
