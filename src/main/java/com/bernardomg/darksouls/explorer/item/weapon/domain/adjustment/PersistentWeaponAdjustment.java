
package com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment;

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
@Entity(name = "WeaponAdjustment")
@Table(name = "weapon_adjustments")
public final class PersistentWeaponAdjustment {

    @NonNull
    @Column(name = "adjustment", nullable = false)
    private Integer adjustment   = 0;

    @NonNull
    @Column(name = "faith", nullable = false)
    private Integer faith        = 0;

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long    id;

    @NonNull
    @Column(name = "intelligence", nullable = false)
    private Integer intelligence = 0;

    @NonNull
    @Column(name = "name", nullable = false)
    private String  name;

}
