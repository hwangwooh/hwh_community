package com.example.hwh_community.account;

import com.example.hwh_community.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByEmail(String email);
    Account findByNickname(String Nickname);
}
