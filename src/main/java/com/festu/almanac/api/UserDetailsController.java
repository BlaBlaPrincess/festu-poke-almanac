package com.festu.almanac.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserDetailsController {

    @GetMapping("/authorities")
    public Collection<? extends GrantedAuthority> login(Authentication authentication) {
        return authentication.getAuthorities();
    }

    @GetMapping("/admin")
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                             .stream()
                             .map(GrantedAuthority::getAuthority)
                             .collect(Collectors.toList())
                             .contains("ADMIN");
    }

}
