package com.festu.almanac.service;

import com.festu.almanac.model.QUserDetailsEntity;
import com.festu.almanac.model.UserDetailsEntity;
import com.festu.almanac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    private final QUserDetailsEntity qEntity = QUserDetailsEntity.userDetailsEntity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsEntity> user = repository.findOne(qEntity.username.eq(username));

        user.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        return user.get();
    }
}
