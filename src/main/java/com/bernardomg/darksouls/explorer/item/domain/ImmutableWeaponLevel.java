
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponLevel implements WeaponLevel {

    @NonNull
    private Integer level;

    @NonNull
    private Integer physicalLevel;

    public ImmutableWeaponLevel(@NonNull final Integer level,
            @NonNull final Integer physicalLevel) {
        super();

        this.level = level;
        this.physicalLevel = physicalLevel;
    }

}
