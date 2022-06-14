
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

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
@Entity(name = "Catalyst")
@Table(name = "catalysts")
public final class PersistentCatalyst {

    @NonNull
    @Column(name = "description", nullable = false)
    private String  description;

    @NonNull
    @Column(name = "dexterity_bonus", nullable = false)
    private String  dexterityBonus;

    @NonNull
    @Column(name = "durability", nullable = false)
    private Integer durability = 0;

    @NonNull
    @Column(name = "faith_bonus", nullable = false)
    private String  faithBonus;

    @NonNull
    @Column(name = "fire_dmg", nullable = false)
    private Integer fireDamage;

    @NonNull
    @Column(name = "fire_reduction", nullable = false)
    private Float   fireReduction;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id         = -1l;

    @NonNull
    @Column(name = "intelligence_bonus", nullable = false)
    private String  intelligenceBonus;

    @NonNull
    @Column(name = "lightning_dmg", nullable = false)
    private Integer lightningDamage;

    @NonNull
    @Column(name = "lightning_reduction", nullable = false)
    private Float   lightningReduction;

    @NonNull
    @Column(name = "magic_dmg", nullable = false)
    private Integer magicDamage;

    @NonNull
    @Column(name = "magic_reduction", nullable = false)
    private Float   magicReduction;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name;

    @NonNull
    @Column(name = "physical_dmg", nullable = false)
    private Integer physicalDamage;

    @NonNull
    @Column(name = "physical_reduction", nullable = false)
    private Float   physicalReduction;

    @NonNull
    @Column(name = "strength_bonus", nullable = false)
    private String  strengthBonus;

    @NonNull
    @Column(name = "weight", nullable = false)
    private Long    weight     = 0l;

}
