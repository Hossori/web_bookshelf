package com.hsr.domain.user.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsr.constant.JpaConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name=JpaConst.TABLE_USER)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String password;
    private Integer gender;
    private LocalDate birthday;
    private String introduction;
    private Long createdEpochSecond;;
    private Integer deleteFlag;

    @Override
    public String getUsername() {
        return this.getEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) { return true; }
        if(obj == null) { return false; }

        if(obj instanceof User) {
            return ((User) obj).getId().equals(this.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
