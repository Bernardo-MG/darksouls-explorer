
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponProgression implements WeaponProgression {

    @NonNull
    private Iterable<WeaponProgressionPath> paths;

    @NonNull
    private String                          name;

}
