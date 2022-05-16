
package com.bernardomg.darksouls.explorer.item.armor.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoArmor implements Armor {

    @NonNull
    private String  description = "";

    @NonNull
    private Integer durability  = 0;

    @NonNull
    private Long    id          = -1l;

    @NonNull
    private String  name        = "";

    @NonNull
    private Long    weight      = 0l;

}
