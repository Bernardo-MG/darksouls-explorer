
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
@Entity(name = "ArmorLevel")
@Table(name = "armor_levels")
public final class PersistentArmorLevel {

    @NonNull
    @Column(name = "bleed_protection", nullable = false)
    private Float   bleedProtection;

    @NonNull
    @Column(name = "curse_protection", nullable = false)
    private Float   curseProtection;

    @NonNull
    @Column(name = "fire_protection", nullable = false)
    private Float   fireProtection;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id = -1l;

    @NonNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @NonNull
    @Column(name = "lightning_protection", nullable = false)
    private Float   lightningProtection;

    @NonNull
    @Column(name = "magic_protection", nullable = false)
    private Float   magicProtection;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name;

    @NonNull
    @Column(name = "poison_protection", nullable = false)
    private Float   poisonProtection;

    @NonNull
    @Column(name = "regular_protection", nullable = false)
    private Float   regularProtection;

    @NonNull
    @Column(name = "slash_protection", nullable = false)
    private Float   slashProtection;

    @NonNull
    @Column(name = "strike_protection", nullable = false)
    private Float   strikeProtection;

    @NonNull
    @Column(name = "thrust_protection", nullable = false)
    private Float   thrustProtection;

}
