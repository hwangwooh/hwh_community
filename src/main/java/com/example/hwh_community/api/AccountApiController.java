package com.example.hwh_community.api;

import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.domain.Account;
import com.example.hwh_community.signup.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountApiController {

    private AccountService accountService;


    @PostMapping("/sign-up")
    public void signUpSubmit(@RequestBody @Valid SignUpForm signUpForm) {

        Account account = accountService.saveNewAccount(signUpForm);
        accountService.login(account);
    }
}
