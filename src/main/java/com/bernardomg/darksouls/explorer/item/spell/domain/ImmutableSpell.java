
package com.bernardomg.darksouls.explorer.item.spell.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ImmutableSpell implements Spell {

    @NonNull
    private String  description  = "";

    @NonNull
    private Integer faith        = 0;

    @NonNull
    private Long    id           = -1l;

    @NonNull
    private Integer intelligence = 0;

    @NonNull
    private String  name         = "";

    @NonNull
    private String  school       = "";

    @NonNull
    private Integer slots        = 0;

    @NonNull
    private Integer uses         = 0;

}
