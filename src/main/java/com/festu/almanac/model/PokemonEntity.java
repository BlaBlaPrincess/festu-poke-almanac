package com.festu.almanac.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonEntity extends UuidEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private byte[] frontImage;

    @Lob
    private byte[] backImage;

}
