package com.hsr.domain.user.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hsr.annotation.SameString;
import com.hsr.annotation.UnregisteredEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SameString(field1="password", field2="rePassword")
public class UserEditForm {
    @NotNull
    private Integer id;
    @Email
    @NotBlank
    @UnregisteredEmail
    private String email;
    @NotBlank
    private String name;
    private String password;
    private String rePassword;
    private Integer gender;
    private String birthday;
    private String introduction;
}
