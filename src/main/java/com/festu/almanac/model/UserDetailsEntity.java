package com.festu.almanac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.festu.almanac.model.converters.GrantedAuthorityListConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsEntity extends UuidEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Convert(converter = GrantedAuthorityListConverter.class)
    private List<GrantedAuthority> authorities;

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
}
