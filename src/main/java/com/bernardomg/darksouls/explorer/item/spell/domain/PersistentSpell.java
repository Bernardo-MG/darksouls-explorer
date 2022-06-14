
package com.bernardomg.darksouls.explorer.item.spell.domain;

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
@Entity(name = "Spell")
@Table(name = "spells")
public final class PersistentSpell {

    @NonNull
    @Column(name = "description", nullable = false)
    private String  description  = "";

    @NonNull
    @Column(name = "faith", nullable = false)
    private Integer faith        = 0;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id           = -1l;

    @NonNull
    @Column(name = "intelligence", nullable = false)
    private Integer intelligence = 0;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name         = "";

    @NonNull
    @Column(name = "school", nullable = false)
    private String  school       = "";

    @NonNull
    @Column(name = "slots", nullable = false)
    private Integer slots        = 0;

    @NonNull
    @Column(name = "uses", nullable = false)
    private Integer uses         = 0;

}
