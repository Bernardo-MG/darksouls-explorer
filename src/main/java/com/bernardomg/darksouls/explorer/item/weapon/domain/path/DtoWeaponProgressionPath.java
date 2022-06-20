
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponProgressionPath implements WeaponProgressionPath {

    @NonNull
    private Iterable<WeaponProgressionLevel> levels;

    @NonNull
    private String                           path;

}
