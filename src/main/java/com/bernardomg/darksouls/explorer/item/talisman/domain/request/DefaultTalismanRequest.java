
package com.bernardomg.darksouls.explorer.item.talisman.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultTalismanRequest implements TalismanRequest {

    @NonNull
    private String name = "";

}
