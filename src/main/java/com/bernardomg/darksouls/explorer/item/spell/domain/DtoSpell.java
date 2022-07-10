
package com.bernardomg.darksouls.explorer.item.spell.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoSpell implements Spell {

    @NonNull
    private String            description  = "";

    @NonNull
    private Long              id           = -1l;

    @NonNull
    private String            name         = "";

    @NonNull
    private SpellRequirements requirements = new DtoSpellRequirements();

    @NonNull
    private String            school       = "";

    @NonNull
    private Integer           slots        = 0;

    @NonNull
    private Integer           uses         = 0;

}
