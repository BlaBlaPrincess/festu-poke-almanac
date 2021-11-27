package com.festu.almanac.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends UuidEntity {

    private String comment;

    @ManyToOne
    @JoinColumn(name = "poke_id")
    private PokemonEntity poke;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserDetailsEntity owner;

}
