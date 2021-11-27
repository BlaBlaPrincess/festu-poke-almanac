package com.festu.almanac.feign;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "pokeApi",
             url = "https://pokeapi.co/api/v2/")
public interface PokeFeignClient {

    @GetMapping("/pokemon/{name}")
    JsonNode getPoke(@PathVariable String name);

}
