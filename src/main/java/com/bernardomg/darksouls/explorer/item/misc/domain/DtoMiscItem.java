
package com.bernardomg.darksouls.explorer.item.misc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoMiscItem implements MiscItem {

    @NonNull
    private String description = "";

    @NonNull
    private Long   id          = -1l;

    @NonNull
    private String name        = "";

}
