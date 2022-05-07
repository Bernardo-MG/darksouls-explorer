
package com.bernardomg.darksouls.explorer.item.armor.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoArmor implements Armor {

    @NonNull
    private Iterable<String> description;

    @NonNull
    private Long             id;

    @NonNull
    private String           name;

    @NonNull
    private ItemStats        stats;

}
