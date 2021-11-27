package com.festu.almanac.api;

import com.festu.almanac.model.CommentEntity;
import com.festu.almanac.model.PokemonEntity;
import com.festu.almanac.service.PokemonAlmanacService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/poke")
@RequiredArgsConstructor
public class PokemonAlmanacController {

    private final PokemonAlmanacService service;

    @GetMapping
    public List<PokemonEntity> getAll() {
        return service.getAll();
    }

    @PostMapping("/comment/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentEntity addComment(@RequestBody String comment, @RequestBody UUID pokeId, Authentication authentication) {
        return service.addComment(comment, pokeId, authentication);
    }

    @PostMapping("/add/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PokemonEntity addPoke(@PathVariable String name) {
        return service.addPoke(name);
    }

    @PostMapping("/comment/remove/{id}")
    public void removeComment(@PathVariable UUID id, Authentication authentication) {
        service.removeComment(id, authentication);
    }

    @PostMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void removePoke(@PathVariable UUID id) {
        service.removePoke(id);
    }

}
