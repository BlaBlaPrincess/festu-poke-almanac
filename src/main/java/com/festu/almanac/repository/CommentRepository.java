package com.festu.almanac.repository;

import com.festu.almanac.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID>,
                                           QuerydslPredicateExecutor<CommentEntity> {
}
