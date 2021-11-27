package com.festu.almanac.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "githubUserContent",
             url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/")
public interface PokeSpritesFeignClient {

    @GetMapping("/pokemon/{name}")
    Response getPokeSprite(@PathVariable String name);

}
