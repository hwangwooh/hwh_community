package com.example.hwh_community.settings;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordForm {

    @Length(min = 8, max = 30)
    private String newPassword;

    @Length(min = 8, max = 30)
    private String newPasswordConfirm;

}
