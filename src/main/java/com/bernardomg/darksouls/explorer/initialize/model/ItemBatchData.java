
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ItemBatchData {

    @NonNull
    private String description = "";

    @NonNull
    private String name        = "";

}
