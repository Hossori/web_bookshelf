package com.hsr.domain.user.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hsr.annotation.SameString;
import com.hsr.annotation.UnregisteredEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SameString(field1="password", field2="rePassword")
public class UserSignupForm {
    @Email
    @NotBlank
    @UnregisteredEmail
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
}
