
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponLevelNode implements WeaponLevelNode {

    @NonNull
    private Integer level;

    @NonNull
    private String  name;

    @NonNull
    private String  path;

    @NonNull
    private Integer pathLevel;

}
