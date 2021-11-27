package com.festu.almanac.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class UuidEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && Hibernate.getClass(this) == Hibernate.getClass(obj)) {
            UuidEntity that = (UuidEntity) obj;
            return Objects.equals(this.getId(), that.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
