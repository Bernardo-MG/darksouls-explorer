
package com.bernardomg.darksouls.explorer.item.weapon.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultWeaponRequest implements WeaponRequest {

    @NonNull
    private String name = "";

}
