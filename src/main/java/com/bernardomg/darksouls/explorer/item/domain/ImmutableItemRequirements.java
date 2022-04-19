
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;

@Data
public final class ImmutableItemRequirements implements ItemRequirements {

    private final Integer dexterity;

    private final Integer faith;

    private final Integer intelligence;

    private final Integer strength;

}
