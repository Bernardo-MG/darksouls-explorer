
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoCatalystSummary implements CatalystSummary {

    @NonNull
    private String description;

    @NonNull
    private Long   id;

    @NonNull
    private String name;

}
