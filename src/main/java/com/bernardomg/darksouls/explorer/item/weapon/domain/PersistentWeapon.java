
package com.bernardomg.darksouls.explorer.item.weapon.domain;

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
@Entity(name = "Weapon")
@Table(name = "weapons")
public final class PersistentWeapon implements Weapon {

    @NonNull
    @Column(name = "description", nullable = false)
    private String  description;

    @NonNull
    @Column(name = "dexterity", nullable = false)
    private Integer dexterity;

    @NonNull
    @Column(name = "durability", nullable = false)
    private Integer durability = 0;

    @NonNull
    @Column(name = "faith", nullable = false)
    private Integer faith;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id;

    @NonNull
    @Column(name = "intelligence", nullable = false)
    private Integer intelligence;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name;

    @NonNull
    @Column(name = "strength", nullable = false)
    private Integer strength;

    @NonNull
    @Column(name = "weight", nullable = false)
    private Long    weight     = 0l;

}
