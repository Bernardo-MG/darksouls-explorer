
package com.bernardomg.darksouls.explorer.item.spell.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ImmutableSpell implements Spell {

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
