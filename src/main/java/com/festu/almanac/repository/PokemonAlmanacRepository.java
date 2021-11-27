package com.festu.almanac.repository;

import com.festu.almanac.model.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface PokemonAlmanacRepository extends JpaRepository<PokemonEntity, UUID>,
                                                  QuerydslPredicateExecutor<PokemonEntity> {
}
