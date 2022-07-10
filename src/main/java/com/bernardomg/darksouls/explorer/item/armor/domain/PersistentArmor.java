
package com.bernardomg.darksouls.explorer.item.armor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity(name = "Armor")
@Table(name = "armors")
public final class PersistentArmor {

    @NonNull
    @Column(name = "description", nullable = false)
    private String  description = "";

    @NonNull
    @Column(name = "durability", nullable = false)
    private Integer durability  = 0;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id          = -1l;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name        = "";

    @NonNull
    @Column(name = "weight", nullable = false)
    private Long    weight      = 0l;

}
