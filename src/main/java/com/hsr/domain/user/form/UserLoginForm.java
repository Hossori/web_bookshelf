package com.hsr.domain.user.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginForm {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
