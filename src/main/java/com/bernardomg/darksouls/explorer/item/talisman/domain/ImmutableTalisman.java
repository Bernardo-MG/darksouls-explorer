
package com.bernardomg.darksouls.explorer.item.talisman.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableTalisman implements Talisman {

    @NonNull
    private final Iterable<String> description;

    @NonNull
    private final Long             id;

    @NonNull
    private final String           name;

    @NonNull
    private final ItemStats        stats;

}
