
package com.bernardomg.darksouls.explorer.item.shield.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoSpell implements Spell {

    @NonNull
    private String  description;

    @NonNull
    private Integer dexterity;

    @NonNull
    private Integer durability = 0;

    @NonNull
    private Integer faith;

    @NonNull
    private Long    id;

    @NonNull
    private Integer intelligence;

    @NonNull
    private String  name;

    @NonNull
    private Integer strength;

    @NonNull
    private Long    weight     = 0l;

}
