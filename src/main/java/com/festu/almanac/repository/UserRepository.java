package com.festu.almanac.repository;

import com.festu.almanac.model.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDetailsEntity, UUID>,
                                        QuerydslPredicateExecutor<UserDetailsEntity> {
}
