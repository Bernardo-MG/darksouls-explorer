
package com.bernardomg.darksouls.explorer.item.talisman.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableTalisman implements Talisman {

    @NonNull
    private final String description;

    @NonNull
    private Integer      durability = 0;

    @NonNull
    private final Long   id;

    @NonNull
    private final String name;

    @NonNull
    private Long         weight     = 0l;

}
