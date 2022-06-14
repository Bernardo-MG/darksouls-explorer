
package com.bernardomg.darksouls.explorer.item.ammunition.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DtoAmmunition implements Ammunition {

    @NonNull
    private String description = "";

    @NonNull
    private Long   id          = -1l;

    @NonNull
    private String name        = "";

    @NonNull
    private String type        = "";

}
