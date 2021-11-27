package com.festu.almanac.service;

import com.festu.almanac.model.CommentEntity;
import com.festu.almanac.model.PokemonEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface PokemonAlmanacService {
    List<PokemonEntity> getAll();
    List<CommentEntity> getAllComments(UUID pokeId);
    CommentEntity addComment(String comment, UUID pokeId, Authentication authentication);
    PokemonEntity addPoke(String name);
    void removeComment(UUID id,Authentication authentication);
    void removePoke(UUID id);
}
