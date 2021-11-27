package com.festu.almanac.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.festu.almanac.feign.PokeFeignClient;
import com.festu.almanac.feign.PokeSpritesFeignClient;
import com.festu.almanac.model.*;
import com.festu.almanac.repository.CommentRepository;
import com.festu.almanac.repository.PokemonAlmanacRepository;
import com.festu.almanac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonAlmanacServiceImpl implements PokemonAlmanacService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PokemonAlmanacRepository pokeRepository;

    private final PokeFeignClient pokeClient;
    private final PokeSpritesFeignClient pokeSpritesClient;

    private final QUserDetailsEntity qUserEntity = QUserDetailsEntity.userDetailsEntity;
    private final QCommentEntity qCommentEntity = QCommentEntity.commentEntity;

    @Override
    public List<PokemonEntity> getAll() {
        return pokeRepository.findAll();
    }

    @Override
    public List<CommentEntity> getAllComments(UUID pokeId) {
        List<CommentEntity> result = new ArrayList<>();
        commentRepository.findAll(qCommentEntity.poke.id.eq(pokeId))
                         .forEach(result::add);
        return result;
    }

    @Override
    public CommentEntity addComment(String comment, UUID pokeId, Authentication authentication) {
        UserDetailsEntity owner = resolveUser(authentication);
        Optional<PokemonEntity> poke = pokeRepository.findById(pokeId);
        poke.orElseThrow(() -> new RuntimeException(String.format("Poke with id %s not found", pokeId)));
        CommentEntity commentEntity = CommentEntity.builder()
                                                   .comment(comment)
                                                   .owner(owner)
                                                   .poke(poke.get())
                                                   .build();
        commentEntity = commentRepository.save(commentEntity);
        return commentEntity;
    }

    @Override
    public PokemonEntity addPoke(String name) {
        JsonNode response = pokeClient.getPoke(name);
        JsonNode sprites = response.get("sprites");
        byte[] frontImage = getPokeSprite(sprites, "front_default");
        byte[] backImage = getPokeSprite(sprites, "back_default");

        PokemonEntity poke = PokemonEntity.builder()
                                          .name(name)
                                          .frontImage(frontImage)
                                          .backImage(backImage)
                                          .build();
        poke = pokeRepository.save(poke);
        return poke;
    }

    @Override
    public void removeComment(UUID id, Authentication authentication) {
        UserDetailsEntity user = resolveUser(authentication);
        Optional<CommentEntity> comment = commentRepository.findById(id);
        comment.orElseThrow(() -> new RuntimeException(String.format("Comment with id %s not found", id)));
        if (comment.get().getOwner().getId() != user.getId() &&
            authentication.getAuthorities()
                          .stream()
                          .map(GrantedAuthority::getAuthority)
                          .noneMatch(it -> Objects.equals(it, "ADMIN"))) {
            throw new RuntimeException("User have no permissions to remove comments");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public void removePoke(UUID id) {
        pokeRepository.deleteById(id);
    }

    private byte[] getPokeSprite(JsonNode json, String field) {
        String uri = json.get(field).asText();
        String source = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        String name = uri.replace(source, "");
        try {
            InputStream stream = pokeSpritesClient.getPokeSprite(name)
                                                  .body()
                                                  .asInputStream();
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private UserDetailsEntity resolveUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<UserDetailsEntity> user = userRepository.findOne(qUserEntity.username.eq(username));
        user.orElseThrow(() -> new RuntimeException("Authentication error"));
        return user.get();
    }

}
