package com.festu.almanac;

import com.festu.almanac.model.UserDetailsEntity;
import com.festu.almanac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class Users implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        UserDetailsEntity user = UserDetailsEntity.builder()
                                                  .username("user")
                                                  .password(encoder.encode("pass"))
                                                  .build();
        UserDetailsEntity admin = UserDetailsEntity.builder()
                                                   .username("admin")
                                                   .password(encoder.encode("pass"))
                                                   .authorities(
                                                           Collections.singletonList(
                                                                   new SimpleGrantedAuthority("ADMIN")
                                                                                    )
                                                               )
                                                   .build();
        repository.save(user);
        repository.save(admin);
    }
}
