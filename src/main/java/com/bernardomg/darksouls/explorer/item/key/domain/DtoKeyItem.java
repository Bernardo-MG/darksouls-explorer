
package com.bernardomg.darksouls.explorer.item.key.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoKeyItem implements KeyItem {

    @NonNull
    private String description = "";

    @NonNull
    private Long   id          = -1l;

    @NonNull
    private String name        = "";

}
