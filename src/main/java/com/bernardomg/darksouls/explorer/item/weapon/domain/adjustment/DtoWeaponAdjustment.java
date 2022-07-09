
package com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment;

import java.util.Collections;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponAdjustment implements WeaponAdjustment {

    @NonNull
    private Iterable<WeaponAdjustmentLevel> levels = Collections.emptyList();

    @NonNull
    private String                          name   = "";

}
