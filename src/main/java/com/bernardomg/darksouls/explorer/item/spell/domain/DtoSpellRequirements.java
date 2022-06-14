
package com.bernardomg.darksouls.explorer.item.spell.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoSpellRequirements implements SpellRequirements {

    @NonNull
    private Integer faith        = 0;

    @NonNull
    private Integer intelligence = 0;

}
