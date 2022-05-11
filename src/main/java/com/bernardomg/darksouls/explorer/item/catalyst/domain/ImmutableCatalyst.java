
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableCatalyst implements Catalyst {

    @NonNull
    private final Iterable<String> description;

    @NonNull
    private final Long             id;

    @NonNull
    private final String           name;

    @NonNull
    private final ItemStats        stats;

}
