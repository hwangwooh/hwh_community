package com.example.hwh_community.settings;

import com.example.hwh_community.account.AccountService;
import com.example.hwh_community.account.CurrentAccount;
import com.example.hwh_community.domain.Account;



import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SettingsController {
    public static final String SETTINGS_PROFILE_URL = "settings/profile" ;
    public static final String SETTINGS_PROFILE_VIEW_NAME = "/settings/profile";

    public static final String SETTINGS_PASSWORD_VIEW_NAME = "/settings/password";

    static final String SETTINGS_ACCOUNT_VIEW_NAME = "settings/account";


    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final NicknameValidator nicknameValidator;
    @InitBinder("passwordForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordFormValidator());
    }
    @InitBinder("nicknameForm")
    public void nicknameFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(nicknameValidator);

    }
    @GetMapping("/settings/profile")
    public String profileUpdateForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account,Profile.class));
        return SETTINGS_PROFILE_URL;
    }

    @PostMapping("/settings/profile")
    public String updateProfile(@CurrentAccount Account account, @Valid Profile profile
            , Errors errors, Model model, RedirectAttributes attributes) {
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTINGS_PROFILE_VIEW_NAME;

        }
        accountService.updateProfile(account, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다");
        return "redirect:"+"profile";

    }

    @GetMapping("/settings/password")
    public String updatePasswordForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PasswordForm());
        return SETTINGS_PASSWORD_VIEW_NAME;
    }

    @PostMapping("/settings/password")
    public String updatePassword(@CurrentAccount Account account, @Valid PasswordForm passwordForm, Errors errors,
                                 Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return SETTINGS_PASSWORD_VIEW_NAME;
        }

        accountService.updatePassword(account, passwordForm.getNewPassword());
        attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
        return "redirect:"+"password";
    }


    @GetMapping("/settings/account")
    public String updateAccountForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, NicknameForm.class));
        return SETTINGS_ACCOUNT_VIEW_NAME;
    }

    @PostMapping("/settings/account")
    public String updateAccount(@CurrentAccount Account account, @Valid NicknameForm nicknameForm, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return SETTINGS_ACCOUNT_VIEW_NAME;
        }

        accountService.updateNickname(account, nicknameForm.getNickname());
        attributes.addFlashAttribute("message", "닉네임을 수정했습니다.");
        return "redirect:" + "account";
    }



}
