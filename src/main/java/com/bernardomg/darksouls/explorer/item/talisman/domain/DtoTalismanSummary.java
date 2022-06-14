
package com.bernardomg.darksouls.explorer.item.talisman.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoTalismanSummary implements TalismanSummary {

    @NonNull
    private String description = "";

    @NonNull
    private Long   id          = -1l;

    @NonNull
    private String name        = "";

}
