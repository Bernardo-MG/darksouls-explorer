
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ArmorBatchData {

    @NonNull
    private String  description = "";

    @NonNull
    private Integer durability  = 0;

    @NonNull
    private String  name        = "";

    @NonNull
    private Double  weight      = 0d;

}
